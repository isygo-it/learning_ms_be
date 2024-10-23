package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Workflow;

import java.util.List;

/**
 * The interface Workflow service.
 */
public interface IWorkflowService extends ICrudServiceMethods<Workflow> {
    /**
     * Gets workflow not associated.
     *
     * @return the workflow not associated
     */
    List<String> getWorkflowNotAssociated();


}
