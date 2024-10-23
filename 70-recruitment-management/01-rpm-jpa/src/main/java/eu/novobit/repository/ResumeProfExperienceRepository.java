package eu.novobit.repository;

import eu.novobit.model.ResumeProfExperience;
import org.springframework.stereotype.Repository;

/**
 * The interface Resume prof experience repository.
 */
@Repository
public interface ResumeProfExperienceRepository extends JpaPagingAndSortingRepository<ResumeProfExperience, Long> {
}
