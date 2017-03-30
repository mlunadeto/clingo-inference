package it.eng.plan;

import it.eng.datastructure.Hypothesis;

/**
 * Classe che effettua la strategia
 * @author Andolfo
 *
 */
public abstract class Plan {

	
	private boolean success=false;

	protected abstract boolean isActivable(String key);
	
	
	/**
	 * metodo che chiama una strategia specializzata
	 * @param informationSet
	 */
	public void action(Hypothesis informationSet){
		
		planStrategy(informationSet);
		
		
	}
	
	/**
	 * Implementa la strategia di prelievo delle informazioni
	 * Passo 1: Chiama la query corrispondente al pattern
	 * Passo 2: Preleva le informazioni e le immagazzina nell'informationSet
	 * @param informationSet
	 */
	protected abstract void planStrategy(Hypothesis informationSet);

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	


}
