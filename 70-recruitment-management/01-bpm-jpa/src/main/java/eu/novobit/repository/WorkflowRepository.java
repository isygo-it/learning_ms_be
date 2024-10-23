package eu.novobit.repository;

import eu.novobit.model.Workflow;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Workflow repository.
 */
public interface WorkflowRepository extends JpaPagingAndSortingSASCodifiableRepository<Workflow, Long> {

    /**
     * Find workflow not associated list.
     *
     * @return the list
     */
    @Query("SELECT W.code from Workflow  W where W.code not in (select workflow from WorkflowBoard )")
    List<String> findWorkflowNotAssociated();
}
