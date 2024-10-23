package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.WorkflowState;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.WorkflowStateRepository;
import eu.novobit.service.IWorkflowStateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Workflow state service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = WorkflowStateRepository.class)
public class WorkflowStateService extends AbstractCrudCodifiableService<WorkflowState, WorkflowStateRepository>
        implements IWorkflowStateService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(WorkflowState.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RWFS")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
