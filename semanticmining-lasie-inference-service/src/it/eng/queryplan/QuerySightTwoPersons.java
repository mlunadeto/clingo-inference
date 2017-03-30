package it.eng.queryplan;

import java.util.ArrayList;
import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.test.ClingoTest;
/**
 * Verifica se sono state avvistate due persone vicino la casa della vittima
 * @author Andolfo
 *
 */
public final class QuerySightTwoPersons extends QueryPlan {
	
	
	private final static String timeStep5="5";
	
	
	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void planStrategy(Hypothesis informationSet) {

		/*
		 * task 6: preleva l'ultimo avvistamento di persone vicino il luogo di scomparsa della Lawrence in cui la vittima non c'è
		 */
		
		List<Entity> entitiesSighting=getEntitiesFromCategory("Sighting");
		
		List<Entity>sightingEntitiesWithoutVictim= getEntitiesWithNoInolvedEntities(entitiesSighting, ClingoTest.victimUUid);

		Entity sightingEntityWithoutVictim=getMostRecentEntity(sightingEntitiesWithoutVictim);

		/*
		 * Aggiungi la lista di persone coinvolte
		 */
		//List<Entity>involvedSightingPersons=new ArrayList<>();
		List<MetaData> metaDatasSighting=getMetaValue(sightingEntityWithoutVictim, "PersonRecognized", "Location", "Time");
		
		List<String>personsValue = new ArrayList<>();
		String location=null;
		String timeEvent=null;
		for(MetaData metaData: metaDatasSighting){
			
			if(metaData.getMetaName().equals("PersonRecognized")){
				String[]personsUUid=splitValue(metaData.getMetaValue());
				for(String uuid: personsUUid){
					personsValue.add(getEntity(uuid).getDescription());
				}
			}else if(metaData.getMetaName().equals("Location")){
				location=metaData.getMetaValue();
			}
			else if(metaData.getMetaName().equals("Time")){
				timeEvent=metaData.getMetaValue();
			}
			
			
		}
		
		if(personsValue.isEmpty() || location==null || timeEvent==null){
			setSuccess(false);
			return;
		}
		
		if(personsValue.size()<2){
			setSuccess(false);
			return;
		}
		
		informationSet.addLocation(location);
		
		informationSet.addTimeEvent(timeEvent);
		
		String[] persons = new String[personsValue.size()];
		persons = personsValue.toArray(persons);
		
		for(String person: persons){
			informationSet.addPerson(person);
		}
			
		
		
		String event="happens(sighting(\""+persons[0]+"\",\""+location+"\",\""+persons[1]+"\",\""+timeEvent+"\"),"+timeStep5+").";
		
		
		informationSet.addEvent(event);
		
		setSuccess(true);

	}

}
