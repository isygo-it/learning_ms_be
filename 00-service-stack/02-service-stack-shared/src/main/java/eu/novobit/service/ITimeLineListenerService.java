package eu.novobit.service;

import eu.novobit.model.ITLEntity;


/**
 * The interface Time line listener service.
 */
public interface ITimeLineListenerService {


    /**
     * Perform post persist tl.
     *
     * @param entity the entity
     */
    public void performPostPersistTL(ITLEntity entity);

    /**
     * Perform post remove tl.
     *
     * @param entity the entity
     */
    public void performPostRemoveTL(ITLEntity entity);

    /**
     * Perform post update tl.
     *
     * @param entity the entity
     */
    public void performPostUpdateTL(ITLEntity entity);

}
