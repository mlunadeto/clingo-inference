package it.eng.queryplan;

import java.security.InvalidKeyException;

public class QueryManager {

	/**
	 * ottiene il piano attivabile in base alla chiave
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static QueryPlan  getActivablePlan(String key) throws InvalidKeyException{

		if(key.equals("bootStrap")){
			return new QueryBootStrap();
		}else if(key.equals("lastSeen")){
			return new QueryLastSeen();
		}else if(key.equals("wasSent")){
			return new QueryWasSent();
		}else if(key.equals("liveKnow")){
			return new QueryLiveKnow();
		}else if(key.equals("wasWith")){
			return new QueryWasWith();
		}else if (key.equals("findEvidence")){
			return new QueryFindEvidence();
		}else if(key.equals("sightTwo")){
			return new QuerySightTwoPersons();
		}
		else{
			throw new InvalidKeyException("Chiave passata errata");
		}


	}

}
