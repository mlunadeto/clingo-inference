package it.eng.queryplan;

import java.util.List;
import java.util.Set;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.test.ClingoTest;



/**
 * La vittima conosce un gruppo e questo gruppo dove vive?
 * 
 * @author Andolfo
 *
 */
public final class QueryLiveKnow extends QueryPlan {




	private final static String timeStamp2="2";

	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void planStrategy(Hypothesis informationSet) {


		/*
		 * Prelevo gli eventi di categoria Knowing
		 */
		List<Entity> entitiesKnowing=getEntitiesFromCategory("Knowing");


		if(entitiesKnowing.isEmpty()|| entitiesKnowing==null){
			setSuccess(false);
			return;
		}

		/*
		 * Tra tutti gli eventi prelevo quelli in cui la vittima è coinvolta
		 */
		entitiesKnowing=getEntitiesWithInvolvedEntities2(entitiesKnowing, ClingoTest.victimUUid);
		if(entitiesKnowing==null){
			setSuccess(false);
			return;
		}


		/*
		 * Per ogni evento della lista vedo chi vive nella location che non è la lawrence
		 * 
		 */
		EXTFOR: for(Entity entityKnowing:entitiesKnowing){
			/*
			 * Prelevo i gruppi e le persone coinvolte nell'evento
			 */
			List<Entity> entityPersons=getEntitiesFromMetaNames(entityKnowing,"PersonRecognized", "GroupRecognized");


			for(Entity personRecognized:entityPersons){
				if(!personRecognized.getUuid().equals(ClingoTest.victimUUid)){

					Entity person=personRecognized; //group recognized
					/*
					 * Prelevo l'abitazione del gruppo/persona
					 */
					List<MetaData>metaDataLivesIn=getMetaValue(person, "LivesIn");

					if(metaDataLivesIn.isEmpty()|| metaDataLivesIn==null){
						setSuccess(false);
						return;
					}


					String livesInValue=metaDataLivesIn.size()>0?metaDataLivesIn.get(0).getMetaValue():"";

					if(livesInValue!=null){
						/*
						 * Controllo che esiste un valore simile nell'information set
						 */
						Set<String>events=informationSet.getEvents();
						for(String event: events){
							//se un evento di invio di una ruisorsa contiene questo valore 
							if(event.contains(livesInValue)&& event.contains(QueryWasSent.keyWasSent)){
								String groupDescription=person.getDescription();
								informationSet.addGroupt(groupDescription);

								String victimName=getEntity(ClingoTest.victimUUid).getName();
								String eventKnow="happens(happensToKnow(\""+victimName+"\",\""+groupDescription+"\"),"+timeStamp2+").";
								informationSet.addEvent(eventKnow);	
								String eventLive="happens(happensToLive(\""+groupDescription+"\",\""+livesInValue+"\"),"+timeStamp2+").";
								informationSet.addEvent(eventLive);
								setSuccess(true);
								break EXTFOR;
							}
						}

					}else{
						setSuccess(false);
						return;
					}


				}


			}

		}




	}

}
