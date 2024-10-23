package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.WorkflowTransition;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.WorkflowTransitionRepository;
import eu.novobit.service.IWorkflowTransitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Workflow transition service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = WorkflowTransitionRepository.class)
public class WorkflowTransitionService extends AbstractCrudCodifiableService<WorkflowTransition, WorkflowTransitionRepository>
        implements IWorkflowTransitionService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(WorkflowTransition.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RWFT")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

}
