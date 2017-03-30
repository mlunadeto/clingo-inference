package it.eng.queryplan;

import java.util.Arrays;
import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.test.ClingoTest;
/**
 * Piano per vedere se nella kb c'è un evento in cui una persona ha inviato qualcosa alla vittima e in quale luogo
 * @author Andolfo
 *
 */
public final class QueryWasSent extends QueryPlan {

	private final static String sendCategory="Send";
	
	public final static String keyWasSent="wasSent";

	private final static String timeStamp1="1";


	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void planStrategy(Hypothesis informationSet) {

		

		List<Entity> entitiesSending=super.getEntitiesFromCategory(sendCategory);
		
		if(entitiesSending.isEmpty()|| entitiesSending==null){
			setSuccess(false);
			return;
		}
		
		/*
		 * Per ogni evento prelevo quelli con la Lawrence coninvolta
		 */		
		List<Entity> subEntitiesSending=getEntitiesWithInvolvedEntities2(entitiesSending,ClingoTest.victimUUid);

		if(subEntitiesSending.isEmpty()||subEntitiesSending==null){
			setSuccess(false);
			return;
		}
		/*
		 * Fornisci l'evento più recente
		 */
		Entity lastSending=getMostRecentEntity(subEntitiesSending);



		/*
		 * Prelevo i metaData Rilevanti nell'evento
		 */
		List<MetaData>relevantMetaDatas=getMetaValue(lastSending,"PersonRecognized","ObjectRecognized","Location");

		/*
		 * Prelevo le corrispettive entià relative ai metaDati estratti
		 */
		//List<Entity> involedEntities=new ArrayList<>();
		String locationValue = null;
		String[]personsValue = null;
		String objectValue = null;
		for(MetaData relevantMetaData: relevantMetaDatas){
			if(relevantMetaData.getMetaName().equals("Location")){
				locationValue=relevantMetaData.getMetaValue();
			}
			else if(relevantMetaData.getMetaName().equals("PersonRecognized")){
				personsValue=super.splitValue(relevantMetaData.getMetaValue());

			}else if(relevantMetaData.getMetaName().equals("ObjectRecognized")){
				objectValue=relevantMetaData.getMetaValue();
			}
		}
		
		/*
		 * Se uno dei meta value è nullo o il numero di Persone è inferiore a due o 
		 * tra le persone non c'è la vittima, il piano fallisce
		 */
		if(locationValue==null || objectValue==null || personsValue.length<2 || !Arrays.asList(personsValue).contains(ClingoTest.victimUUid)){
			setSuccess(false);
			return;
		}
		
		/*
		 * Riempio le informazioni nell'informaiton set
		 */
		Entity object=getEntity(objectValue);
		
		String objectDescription=object.getDescription();
		
		informationSet.addResource(objectDescription);
		
		String personDescription=null;
		String victimDescription=null;
		for(String personValue: personsValue){
			Entity person=getEntity(personValue);
			if(!person.getUuid().equals(ClingoTest.victimUUid)){
				personDescription=person.getDescription();
				informationSet.addPerson(personDescription);
			}else{
				victimDescription=person.getName();
			}
				
		}
		
		
		informationSet.addLocation(locationValue);
		

		String eventWasSent="happens(wasSent(\""+objectDescription+"\",\""+locationValue+"\", \""+personDescription+"\",\""+victimDescription+"\"),"+timeStamp1+").";

		//System.out.println(eventWasSent);
		
		informationSet.addEvent(eventWasSent);
		
		setSuccess(true);
	
	}

}
