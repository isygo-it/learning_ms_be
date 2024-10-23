package eu.novobit.repository;

import eu.novobit.model.VCalendar;

import java.util.Optional;

/**
 * The interface V calendar repository.
 */
public interface VCalendarRepository extends JpaPagingAndSortingSASRepository<VCalendar, Long> {

    /**
     * Find by domain ignore case and name optional.
     *
     * @param domain the domain
     * @param name   the name
     * @return the optional
     */
    Optional<VCalendar> findByDomainIgnoreCaseAndName(String domain, String name);
}
