package eu.novobit.repository;

import eu.novobit.model.ResumeEducation;
import org.springframework.stereotype.Repository;

/**
 * The interface Resume education repository.
 */
@Repository
public interface ResumeEducationRepository extends JpaPagingAndSortingRepository<ResumeEducation, Long> {
}
