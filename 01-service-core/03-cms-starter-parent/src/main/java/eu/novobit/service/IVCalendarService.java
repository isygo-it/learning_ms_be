package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.VCalendar;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * The interface Iv calendar service.
 */
public interface IVCalendarService extends ICrudServiceMethods<VCalendar> {

    /**
     * Find by domain and name v calendar.
     *
     * @param domain the domain
     * @param name   the name
     * @return the v calendar
     */
    VCalendar findByDomainAndName(String domain, String name);

    /**
     * Download resource.
     *
     * @param domain the domain
     * @param name   the name
     * @return the resource
     * @throws IOException the io exception
     */
    Resource download(String domain, String name) throws IOException;
}
