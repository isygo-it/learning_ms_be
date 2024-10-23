package eu.novobit.listener;

import eu.novobit.model.ITLEntity;
import eu.novobit.service.ITimeLineListenerService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * The type Time line listener.
 */
@Component
public class TimeLineListener {

    private static ITimeLineListenerService timeLineListenerService;

    /**
     * Init.
     *
     * @param timeLineListenerService the time line listener service
     */
    @Nullable
    @Autowired
    public void init(ITimeLineListenerService timeLineListenerService) {
        this.timeLineListenerService = timeLineListenerService;
    }

    /**
     * On post persist.
     *
     * @param entity the entity
     */
    @PostPersist
    void onPostPersist(Object entity) {
        if (entity instanceof ITLEntity itlEntity) {
            timeLineListenerService.performPostPersistTL(itlEntity);
        }
    }

    /**
     * On post remove.
     *
     * @param entity the entity
     */
    @PostRemove
    void onPostRemove(Object entity) {
        if (entity instanceof ITLEntity itlEntity) {
            timeLineListenerService.performPostRemoveTL(itlEntity);
        }
    }

    /**
     * On post update.
     *
     * @param entity the entity
     */
    @PostUpdate
    void onPostUpdate(Object entity) {
        if (entity instanceof ITLEntity itlEntity) {
            timeLineListenerService.performPostUpdateTL(itlEntity);
        }
    }
}
