package eu.novobit.remote.cms;

import eu.novobit.api.CalendarEventControllerAPI;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Cms calendar event service.
 */
@FeignClient(configuration = FeignConfig.class, name = "calendar-service", contextId = "cms-calendar-event", path = "/api/private/calendar/event")
public interface CmsCalendarEventService extends CalendarEventControllerAPI {

}
