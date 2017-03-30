package it.eng.query;

import java.util.List;

import it.eng.hybrid.model.era.data.Entity;
import it.eng.hybrid.model.era.data.MetaData;
import it.eng.hybrid.test.config.Configurator2;
import it.eng.util.RestUtil;
public interface Query<E extends Entity, M extends MetaData> {


	Configurator2 configurator2=Configurator2.getInstance();

	RestUtil restUtil =RestUtil.getInstance();


	/**
	 * Preleva una entità dato il suo uuid
	 * @param uuid
	 * @return
	 */
	E getEntity(String uuid);


	/**
	 * Preleva una lista di risultati data una category
	 * @param category
	 * @return
	 */
	List<E> getEntitiesFromCategory(String category);



	/**
	 * Ritorna la lista di entità che sono riconosciute nei mataDati dell'entità entity
	 * @param entity
	 * @param metaName
	 * @return
	 */
	@Deprecated
	List<Entity> getEntitiesFromMetaNames(Entity entity,String...metaName);


	/**
	 * Data una lista di entità, ritorna una sottolista che ha nei metadati gli uuid specificati
	 * @param entities
	 * @param uuids
	 * @return
	 */
	@Deprecated
	List<Entity> getEntitiesWithInvolvedEntities(List<Entity> entities, String...uuids);


	/**
	 * versione diversificata per gli array di persone. 
	 * @param entities: una lista di tipi generici che estendono entita
	 * @param uuids
	 * @return
	 */
	List<E> getEntitiesWithInvolvedEntities2(List<? extends E> entities, String...uuids);

	/**
	 * Data una lista di entità preleva la più recente
	 * @param entities: una lista di tipi generici che estendono entita
	 * @return
	 */
	E getMostRecentEntity(List<? extends E> entities);


	/**
	 * Preleva i metadati da una entity che corrispondono a quel metaName (PersonRecognized, GroupRecognized, ObjectRecognized...)
	 * @param entity
	 * @param metaName
	 * @return
	 */
	List<M> getMetaValue(Entity entity,String...metaName);


	/**
	 * Data una lista di entità, preleva una sottolista in cui gli uuid NON sono coinvolti
	 * @param entities: una lista di tipi generici che estendono entita
	 * @param uuids
	 * @return
	 */
	List<E> getEntitiesWithNoInolvedEntities(List<? extends E> entities, String...uuids);
	
	
}
