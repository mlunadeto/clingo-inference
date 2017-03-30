package it.eng.queryplan;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.datastructure.Result;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.hybrid.query.builder.HQuery;
import it.eng.hybrid.query.builder.HSimpleQuery;
import it.eng.hybrid.query.builder.enums.Criteria;
import it.eng.hybrid.query.builder.enums.HeadField;
import it.eng.hybrid.query.builder.headfilter.HeadFilter;
import it.eng.hybrid.query.builder.headfilter.HeadFilterFactory;
import it.eng.plan.Plan;
import it.eng.query.Query;


/**
 * 
 * @author Andolfo
 *
 */

public abstract  class QueryPlan extends Plan implements Query<Entity,MetaData> {




	@Override
	public List<Entity> getEntitiesFromCategory(String category) {

		String result=null;
		List<Entity> entitiesList=null;

		String searhMetaQueryString=configurator2.getExec()+"ERA_MODEL_SearchMetaQuery/"+configurator2.getInstanceArray()[0];

		URL searchMetaQueryUrl = null;
		try {
			searchMetaQueryUrl = new URL(searhMetaQueryString);

			HSimpleQuery hSimpleQuery=new HSimpleQuery();




			HeadFilter headFilterSighting=HeadFilterFactory.exact().setCriteria(Criteria.MUST)
					.setHeadField(HeadField.CATEGORY)
					.setHeadStringValue("http://is3lab.eng.it/ontologies/lasie/category.owl#"+category);

			hSimpleQuery.addHeadFilter(headFilterSighting);		


			HQuery hQuery=new HQuery(hSimpleQuery);
			hQuery.setWithHead(true);

			String body=getJsonStringFromHQuery(hQuery);

			result=restUtil.postRest(searchMetaQueryUrl, body);


			/*
			 * Prendo un altro oggetto gson
			 */
			Gson gson=new Gson();
			Result entityResult=gson.fromJson(result,Result.class);
			/*
			 * Aggiunto perchè ERA_MODEL_SearchMetaQuery/ nell'entità, non preleva più di un metadato con lo stesso nome
			 * */
			entitiesList=new ArrayList<>();
			for(Entity entity: entityResult.getEntityList()){
				entitiesList.add(getEntity(entity.getUuid()));
			}




		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entitiesList;

	}



	@Override
	public List<Entity> getEntitiesFromMetaNames(Entity entity,String...metaName){

		List<MetaData> metaDatas=new ArrayList<>();
		List<Entity> entities=new ArrayList<>();
		if (metaName.length>0){

			for(MetaData metaData:entity.getMetaData()){
				if (Arrays.asList(metaName).contains(metaData.getMetaName())){
					metaDatas.add(metaData);
				}
			}
		}


		for(MetaData metaData: metaDatas){
			entities.add(getEntity(metaData.getMetaValue()));
		}
		return entities;
	}





	/**
	 * Data una HQuery me la converte in formato json
	 * @param hQuery
	 * @return
	 */
	private String getJsonStringFromHQuery(HQuery hQuery){

		GsonBuilder gsonBuilder=new GsonBuilder();
		gsonBuilder.registerTypeAdapter(it.eng.hybrid.query.builder.headfilter.HeadFilter.class, new it.eng.hybrid.query.builder.headfilter.deserializer.HeadDeserializer());
		gsonBuilder.registerTypeAdapter(it.eng.hybrid.query.builder.metafilter.MetaFilter.class, new it.eng.hybrid.query.builder.metafilter.deserializer.MetaFilterDeserializer());
		Gson gson=gsonBuilder.create();

		String body=gson.toJson(hQuery);

		return body;

	}


	@Override
	public Entity getEntity(String uuid){

		URL getEntityUrl = null;
		try {
			getEntityUrl = new URL(configurator2.getExec()+
					"ERA_MODEL_GetEntity/"+configurator2.getInstanceArray()[0]+
					"?entityUuid="+uuid);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result=restUtil.postRest(getEntityUrl, "");	

		Gson gson=new Gson();
		Entity entity=gson.fromJson(result, Entity.class);
		return entity;
	}


	@Override
	public List<Entity> getEntitiesWithInvolvedEntities(List<Entity> entities, String...uuids){

		List<Entity> subEntities=new ArrayList<Entity>();
		for(Entity entity: entities){
			List<String>checkedUuids=new ArrayList<>(Arrays.asList(uuids)); //uuid che vengono trovati per ogni entità
			entity=getEntity(entity.getUuid());
			List<MetaData> metaDatas=entity.getMetaData();
			Iterator<MetaData> iterator=metaDatas.iterator();
			WHILE: while(iterator.hasNext()){
				if(checkedUuids.size()==0 ){
					subEntities.add(entity);
					break WHILE;
				}
				String metaValue=iterator.next().getMetaValue();
				if(checkedUuids.contains(metaValue)){
					checkedUuids.remove(metaValue);
				}else if(!checkedUuids.contains(metaValue)){
					continue WHILE;
				}
			}



		}
		return subEntities;

	}


	@Override
	public List<Entity> getEntitiesWithInvolvedEntities2(List<? extends Entity> entities, String...uuids){

		List<Entity> subEntities=new ArrayList<Entity>();
		for(Entity entity: entities){
			List<String>checkedUuids=new ArrayList<>(Arrays.asList(uuids)); //uuid che vengono trovati per ogni entità
			entity=getEntity(entity.getUuid());
			List<MetaData> metaDatas=entity.getMetaData();
			Iterator<MetaData> iterator=metaDatas.iterator();
			WHILE: while(iterator.hasNext()){

				MetaData metaData=iterator.next();
				String metaName=metaData.getMetaName();
				boolean isPerson=false;
				if(metaName.equals("PersonRecognized")){
					isPerson=true;
				}

				if(checkedUuids.size()==0 ){
					subEntities.add(entity);
					break WHILE;
				}
				String metaValue=metaData.getMetaValue();
				if(isPerson){
					String[]personsUuid=splitValue(metaValue);
					FOR:for(String personUuid: personsUuid){
						if(checkedUuids.contains(personUuid)){
							checkedUuids.remove(personUuid);

						}
						else if(!checkedUuids.contains(personUuid)){
							continue FOR;
						}
					}



				}else{ //se non è una persona
					if(checkedUuids.contains(metaValue)){
						checkedUuids.remove(metaValue);
					}else if(!checkedUuids.contains(metaValue)){
						continue WHILE;
					}
				}

			}
		}
		return subEntities;

	}






	/**
	 * Splitta un array di metavalue.
	 * Usato con gli array nei metadati
	 * @param metaValue
	 * @return
	 */
	public String[] splitValue(String metaValue) {
		String[]personsUuid=metaValue.split(",");
		if(personsUuid.length>1){
			personsUuid[0]=personsUuid[0].substring(1);
			personsUuid[1]=personsUuid[1].substring(0, personsUuid[1].length()-1);
		}else{
			personsUuid[0]=personsUuid[0].substring(1,personsUuid[0].length()-1);
		}

		return personsUuid;
	}



	@Override
	public Entity getMostRecentEntity(List<? extends Entity> entities){

		/*
		 * Preleva l'evento con il tempo più recente
		 */
		Collections.sort(entities, new Comparator<Entity>() {

			@Override
			public int compare(Entity o1, Entity o2) {


				Integer startTime1=0,startTime2=0;

				Iterator<MetaData> it1=o1.getMetaData().iterator();

				while(it1.hasNext()){
					MetaData metaData1=it1.next();
					if(metaData1.getMetaName().equals("StartTime")){
						startTime1=new Integer(metaData1.getMetaValue());
						Iterator<MetaData> it2=o2.getMetaData().iterator();
						while(it2.hasNext()){
							MetaData metaData2=it2.next();
							if(metaData2.getMetaType().equals("StartTime")){
								startTime2=new Integer(metaData2.getMetaValue());
								return startTime1.compareTo(startTime2);
							}
						}
					}

				}


				return 0;
			}
		}); 

		Entity lastSeen=entities.get(entities.size() - 1);
		return lastSeen;

	}

	@Override
	public List<MetaData> getMetaValue(Entity entity,String...metaName){

		List<MetaData> metaDatas=new ArrayList<>();

		if (metaName.length>0){

			for(MetaData metaData:entity.getMetaData()){
				if (Arrays.asList(metaName).contains(metaData.getMetaName())){
					metaDatas.add(metaData);
				}
			}
		}


		return metaDatas;
	}



	@Override
	public List<Entity> getEntitiesWithNoInolvedEntities(List<? extends Entity> entities, String...uuids){
		List<Entity> subEntities=new ArrayList<Entity>();

		for(Entity entity: entities){
			int counter=0;
			List<String>checkedUuids=new ArrayList<>(Arrays.asList(uuids));
			/*
			 * appena è un uuid che è contenuto in un metadato fermati
			 */
			List<MetaData> metaDatas=entity.getMetaData();
			Iterator<MetaData> iterator=metaDatas.iterator();			
			WHILE: while(iterator.hasNext()){
				String metaValue=iterator.next().getMetaValue();
				if(checkedUuids.contains(metaValue)){
					counter++;
					break WHILE;
				}
			}
			if(counter==0){
				subEntities.add(entity);
			}
		}
		return subEntities;
	}



}
