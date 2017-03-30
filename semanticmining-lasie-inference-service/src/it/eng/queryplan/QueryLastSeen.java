package it.eng.queryplan;


import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;
import it.eng.test.ClingoTest;

public final class QueryLastSeen extends QueryPlan {





	private final static String sightingCategory="Sighting";

	private final static String timeStamp0="0";





	/*
	 * (non-Javadoc)
	 * Chiedo alla knowledge base quando è stata vista la vittima l'ultima volta e 
	 * aggiorno l'information set
	 * @see it.eng.hybrid.test.clingo.query.QueryPlan#planStrategy(it.eng.hybrid.test.clingo.Hypothesis)
	 */
	@Override
	protected void planStrategy(Hypothesis informationSet) {
		// TODO Auto-generated method stub


		List<Entity> entitiesSighting=super.getEntitiesFromCategory(sightingCategory);

		if(entitiesSighting.isEmpty()|| entitiesSighting==null){
			setSuccess(false);
			return;
		}

		/*
		 * Preleva  i risultati con solo la lawrence
		 */		
		List<Entity> subEntitiesSightingWithLawrence=getEntitiesWithInvolvedEntities(entitiesSighting, 
				ClingoTest.victimUUid!=null?ClingoTest.victimUUid:"");

		if(subEntitiesSightingWithLawrence.isEmpty()|| subEntitiesSightingWithLawrence==null){
			setSuccess(false);
			return;
		}


		/*
		 * Fornisci l'evento più recente
		 */
		Entity lastSeen=getMostRecentEntity(subEntitiesSightingWithLawrence);

		/*
		 * Preleva il luogo associato
		 */
		String location=getMetaValue(lastSeen, "Location").iterator().hasNext()?
				getMetaValue(lastSeen, "Location").iterator().next().getMetaValue():null;
		
		if(location==null){
			setSuccess(false);
			return;
		}
		informationSet.addLocation(location);


		/*
		 * Preleva il tempo associato
		 */
		String time=getMetaValue(lastSeen, "Time").iterator().next().getMetaValue();
		if(time==null){
			setSuccess(false);
			return;
		}
		informationSet.addTimeEvent(time);

		/*
		 * Creo l'evento
		 */
		String lastSeenEvent="happens(lastSeen(\""+informationSet.getVictim()+"\",\""+location+"\", \""+time+"\"),"+timeStamp0+").";

		
		System.out.println(lastSeenEvent);
		
		informationSet.addEvent(lastSeenEvent);
		
		
		setSuccess(true);


	
	}


	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
