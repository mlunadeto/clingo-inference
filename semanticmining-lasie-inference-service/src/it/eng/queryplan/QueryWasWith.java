package it.eng.queryplan;

import java.util.ArrayList;
import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.test.ClingoTest;

/**
 * Controlla se la vittima si è vista con qualcuno da qualche parte
 * @author Andolfo
 *
 */
public final class QueryWasWith extends QueryPlan {


	private final static String timeStemp3="3";

	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void planStrategy(Hypothesis informationSet) {


		/*
		 * Preleva solo gli eventi di tipo Sighting
		 */
		List<Entity> entitiesSighting=getEntitiesFromCategory("Sighting");
		/*
		 * Preleva  i risultati con la Lawrence
		 */		
		List<Entity> subEntitiesSightingWithVictim=getEntitiesWithInvolvedEntities2(entitiesSighting,ClingoTest.victimUUid);



		List<Entity> sightingEntitiesOfVictimWithSomeBody=new ArrayList<>();

		for(Entity sightingEntityWithVictim: subEntitiesSightingWithVictim){
			//List<Entity>  entitySightingPersons=getEntitiesFromMetaNames(sightingEntityWithLawrence, 
			//	"PersonRecognized", "GroupRecognized");
			List<MetaData> metaDatas= getMetaValue(sightingEntityWithVictim,"PersonRecognized", "GroupRecognized");

			String[]persons=null;
			String groupRecognized=null;
			for(MetaData metaData : metaDatas){
				if(metaData.getMetaName().equals("PersonRecognized")){
					persons=splitValue(metaData.getMetaValue());
				}else if(metaData.getMetaName().equals("GroupRecognized")){
					groupRecognized=metaData.getMetaValue();
				}
			}	
			/*
			 * se ho almeno 2 persone o una persona e un gruppo
			 */
			if(persons==null){
				setSuccess(false);
				return;
			}

			if(persons.length>1 || (groupRecognized!=null && persons.length>0)){

				/*
				 * aggiungo alla lista degli eventi di avvistamento della vittima con qualcuno
				 */
				sightingEntitiesOfVictimWithSomeBody.add(sightingEntityWithVictim);


			}else{
				setSuccess(false);
				return;
			}
		}
		/*
		 * Preleva l'evento più recente
		 */

		Entity sightingEntityOfVictimWithSomeBodyMostRecent=getMostRecentEntity(sightingEntitiesOfVictimWithSomeBody);

		List<MetaData> metaDatas= getMetaValue(sightingEntityOfVictimWithSomeBodyMostRecent,"PersonRecognized", "Location", "Time");

		String involvedPerson=null;
		String victimDescription=null;
		String locationValue=null;
		String time=null;
		for(MetaData metaData: metaDatas){
			if(metaData.getMetaName().equals("PersonRecognized")){
				String[]persons=splitValue(metaData.getMetaValue());
				for(String person: persons){
					if(!person.equals(ClingoTest.victimUUid)){
						involvedPerson=getEntity(person).getDescription();
					}else{
						victimDescription=getEntity(person).getName();
					}
				}

			}else if(metaData.getMetaName().equals("Location")){
				locationValue=metaData.getMetaValue();
			}else if(metaData.getMetaName().equals("Time")){
				time=metaData.getMetaValue();
			}

		}


		if(involvedPerson!=null)informationSet.addPerson(involvedPerson);
		if(locationValue!=null)informationSet.addLocation(locationValue);
		if(time!=null)informationSet.addTimeEvent(time);

		if(involvedPerson==null || locationValue==null || time==null){
			setSuccess(false);
			return;
		}



		String event="happens(was(\""+victimDescription+"\",\""+locationValue+"\",\""+involvedPerson+"\",\""+time+"\"),"+timeStemp3+").";	
		informationSet.addEvent(event);
		setSuccess(true);


	}

}
