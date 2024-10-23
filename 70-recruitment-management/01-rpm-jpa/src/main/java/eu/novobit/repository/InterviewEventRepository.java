package eu.novobit.repository;

import eu.novobit.model.Interview;

import java.util.Optional;

/**
 * The interface Interview event repository.
 */
public interface InterviewEventRepository extends JpaPagingAndSortingRepository<Interview, Long> {
    /**
     * Find by job application event id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Interview> findByJobApplicationEvent_Id(Long id);
}
