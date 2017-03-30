package it.eng.queryplan;


import java.util.List;

import it.eng.datastructure.Hypothesis;
import it.eng.hybrid.model.era.data.Entity;


/**
 * 
 * @author Andolfo
 *
 */
public final class QueryBootStrap extends QueryPlan {


	private final static String bootstrapCategory="MissingPerson";


	private  String victimUuid;


	@Override
	protected void planStrategy(Hypothesis informationSet) {


		/*
		 * chiamo il metodo per prelevare dalla kb le entità con la data category
		 */
		List<Entity> entities=super.getEntitiesFromCategory(bootstrapCategory);
		
		if(entities==null || entities.isEmpty()){
			setSuccess(false);
			return;
		}

		/*
		 * Preleva le persone associate (in questo caso ce n'è solo una)
		 */
		
		List<Entity> entitiesInvolved=super.getEntitiesFromMetaNames(entities.iterator().next(), "PersonRecognized");

		Entity victim=entitiesInvolved.iterator().hasNext()?entitiesInvolved.iterator().next():null;

		if (victim==null){
			setSuccess(false);
			return;
		}

		/*
		 * Setto l'uuid della vittima
		 */
		this.victimUuid=victim.getUuid();
		/*
		 * Setto il  nome della vittima
		 */
		informationSet.setVictim(victim.getName());

		setSuccess(true);


	}


	@Override
	protected boolean isActivable(String key) {
		// TODO Auto-generated method stub
		return false;
	}


	public String getVictimUuid() {
		return victimUuid;
	}


	public void setVictimUuid(String victimUuid) {
		this.victimUuid = victimUuid;
	}


}
