package eu.novobit.repository;

import eu.novobit.model.ResumeLinkedFile;
import org.springframework.stereotype.Repository;

/**
 * The interface Resume linked file repository.
 */
@Repository
public interface ResumeLinkedFileRepository extends JpaPagingAndSortingCodifiableRepository<ResumeLinkedFile, Long> {
}
