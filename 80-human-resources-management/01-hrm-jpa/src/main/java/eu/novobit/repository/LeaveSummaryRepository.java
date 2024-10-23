package eu.novobit.repository;

import eu.novobit.model.LeaveSummary;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface Leave summary repository.
 */
@Repository
public interface LeaveSummaryRepository extends JpaPagingAndSortingRepository<LeaveSummary, Long> {

    /**
     * Find by code employee and year optional.
     *
     * @param code the code
     * @param year the year
     * @return the optional
     */
    Optional<LeaveSummary> findByCodeEmployeeAndYear(String code, String year);
}
