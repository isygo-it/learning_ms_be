package eu.novobit.repository;

import eu.novobit.model.EmployeeLinkedFile;
import org.springframework.stereotype.Repository;

/**
 * The interface employee linked file repository.
 */
@Repository
public interface EmployeeLinkedFileRepository extends JpaPagingAndSortingCodifiableRepository<EmployeeLinkedFile, Long> {
}
