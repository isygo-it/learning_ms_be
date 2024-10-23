package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Workflow;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.WorkflowRepository;
import eu.novobit.service.IWorkflowService;
import eu.novobit.service.IWorkflowStateService;
import eu.novobit.service.IWorkflowTransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The type Workflow service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = WorkflowRepository.class)
public class WorkflowService extends AbstractCrudCodifiableService<Workflow, WorkflowRepository> implements IWorkflowService {

    @Autowired
    private IWorkflowStateService workflowStateService;
    @Autowired
    private IWorkflowTransitionService workflowTransitionService;

    @Override
    public Workflow beforeCreate(Workflow workflow) {
        if (!CollectionUtils.isEmpty(workflow.getWorkflowStates())) {
            workflow.setWorkflowStates(this.workflowStateService.create(workflow.getWorkflowStates()));
        }

        if (!CollectionUtils.isEmpty(workflow.getWorkflowTransitions())) {
            workflow.setWorkflowTransitions(this.workflowTransitionService.create(workflow.getWorkflowTransitions()));
        }

        return workflow;
    }

    @Override
    public Workflow beforeUpdate(Workflow workflow) {
        if (!CollectionUtils.isEmpty(workflow.getWorkflowStates())) {
            workflow.setWorkflowStates(this.workflowStateService.saveOrUpdate(workflow.getWorkflowStates()));
        }

        if (!CollectionUtils.isEmpty(workflow.getWorkflowTransitions())) {
            workflow.setWorkflowTransitions(this.workflowTransitionService.saveOrUpdate(workflow.getWorkflowTransitions()));
        }
        return workflow;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Workflow.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RWFL")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }


    @Override
    public List<String> getWorkflowNotAssociated() {
        return repository().findWorkflowNotAssociated();
    }
}
