package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.VCalendarEvent;

import java.util.List;

/**
 * The interface Iv event service.
 */
public interface IVEventService extends ICrudServiceMethods<VCalendarEvent> {

    /**
     * Find by domain and calendar list.
     *
     * @param domain   the domain
     * @param calendar the calendar
     * @return the list
     */
    List<VCalendarEvent> findByDomainAndCalendar(String domain, String calendar);
}
