package eu.novobit.repository;

import eu.novobit.model.JobLinkedFile;
import org.springframework.stereotype.Repository;

/**
 * The interface Job linked file repository.
 */
@Repository
public interface JobLinkedFileRepository extends JpaPagingAndSortingCodifiableRepository<JobLinkedFile, Long> {
}
