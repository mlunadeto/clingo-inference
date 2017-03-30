package it.eng.queryplan;

import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.test.ClingoTest;

/**
 * Trova evidence e informazioni correlate ad esso
 * @author Andolfo
 *
 */
public final class QueryFindEvidence extends QueryPlan {


	private final static String timeStep4="4";


	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void planStrategy(Hypothesis informationSet) {



		/*
		 * task 5: Vedi se è stata ritrovata una evidence, se appartiene a qualcuno, dove è stata trovata
		 */
		Entity finding=getEntitiesFromCategory("Finding").get(0);//per il momento è unico

		if(finding==null){
			setSuccess(false);
			return;
		}


		String evidence=null;
		String location=null;

		String involvedPerson=null;
		String victim=null;


		List<MetaData> metaDatassRecognized=getMetaValue(finding,"ObjectRecognized","PersonRecognized");

		for(MetaData metaData:metaDatassRecognized ){
			String metaName=metaData.getMetaName();
			if(metaName.equals("ObjectRecognized")){

				String metaValue=metaData.getMetaValue();
				String[]objectsValue=splitValue(metaValue);
				for(String objectValue: objectsValue){
					if(getEntity(objectValue).getDescription().contains("DNA")){
						evidence="DNA";
					}else if(getEntity(objectValue).getDescription().toLowerCase().contains("car")){
						location=getEntity(objectValue).getDescription().toLowerCase();
					}
				}
			}else if(metaName.equals("PersonRecognized")){
				String metaValue=metaData.getMetaValue();
				String[]personsValue=splitValue(metaValue);
				for(String personValue: personsValue){
					if(personValue.equals(ClingoTest.victimUUid)){
						victim=getEntity(ClingoTest.victimUUid).getName();
					}else {
						involvedPerson=getEntity(personValue).getDescription();
					}
				}

			}


		}


		if(involvedPerson==null || location==null || evidence==null || victim ==null){
			setSuccess(false);
			return;
		}

		informationSet.addEvidence(evidence);
		informationSet.addLocation(location);
		informationSet.addPerson(involvedPerson);

		String findingEvent="happens(finding(\""+evidence+"\",\""+location+"\"),"+timeStep4+").";
		informationSet.addEvent(findingEvent);

		String happensToBelong="happens(happensToBelongTo(\""+evidence+"\",\""+involvedPerson+"\"),"+timeStep4+").";
		informationSet.addEvent(happensToBelong);


		String happensToBelong2="happens(happensToBelongTo(\""+location+"\",\""+victim+"\"),"+timeStep4+").";
		informationSet.addEvent(happensToBelong2);

		setSuccess(true);


	}

}
