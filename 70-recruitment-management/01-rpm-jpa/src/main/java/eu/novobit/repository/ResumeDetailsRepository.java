package eu.novobit.repository;


import eu.novobit.model.ResumeDetails;
import org.springframework.stereotype.Repository;

/**
 * The interface Resume details repository.
 */
@Repository
public interface ResumeDetailsRepository extends JpaPagingAndSortingRepository<ResumeDetails, Long> {
}
