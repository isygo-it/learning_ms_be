package eu.novobit.service;

import eu.novobit.dto.NotificationDto;
import eu.novobit.model.INotifiableEntity;

import java.io.IOException;


/**
 * The interface Notification listener service.
 */
public interface INotificationListenerService {

    /**
     * Perform post persist notification.
     *
     * @param entity the entity
     */
    public void performPostPersistNotification(INotifiableEntity entity);

    /**
     * Perform post remove notification.
     *
     * @param entity the entity
     */
    public void performPostRemoveNotification(INotifiableEntity entity);

    /**
     * Perform post update notification.
     *
     * @param entity the entity
     */
    public void performPostUpdateNotification(INotifiableEntity entity);

    /**
     * Send notification.
     *
     * @param notificationDtos the notification dtos
     * @throws IOException the io exception
     */
    public void sendNotification(NotificationDto[] notificationDtos) throws IOException;
}
