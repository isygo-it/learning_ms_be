package eu.novobit.repository;

import eu.novobit.model.Resume;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Resume repository.
 */
@Repository
public interface ResumeRepository extends JpaPagingAndSortingSASCodifiableRepository<Resume, Long> {

    /**
     * Find by code optional.
     *
     * @param code the code
     * @return the optional
     */
    Optional<Resume> findByCode(String code);

    /**
     * Find by check cancel false list.
     *
     * @return the list
     */

    long countByCreatedByAndCancelDateNull(String createdBy);

    long countByDomainAndCancelDateNull(String domain);


}
