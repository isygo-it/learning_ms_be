package eu.novobit.remote.cms;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.config.FeignConfig;
import eu.novobit.dto.data.VCalendarDto;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Cms calendar service.
 */
@FeignClient(configuration = FeignConfig.class, name = "calendar-service", contextId = "cms-calendar", path = "/api/private/calendar")
public interface CmsCalendarService extends IMappedCrudApi<VCalendarDto, VCalendarDto> {

}
