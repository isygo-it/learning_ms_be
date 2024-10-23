package eu.novobit.service.impl;

import eu.novobit.model.ITLEntity;
import eu.novobit.service.ITimeLineListenerService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Time line listener service.
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TimeLineListenerService implements ITimeLineListenerService {

    @Override
    public void performPostPersistTL(ITLEntity entity) {

    }

    @Override
    public void performPostRemoveTL(ITLEntity entity) {

    }

    @Override
    public void performPostUpdateTL(ITLEntity entity) {

    }
}

