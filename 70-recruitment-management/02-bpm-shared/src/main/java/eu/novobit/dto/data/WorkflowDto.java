package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumWorkflow;
import eu.novobit.enumerations.IEnumWorkflowCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Workflow dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WorkflowDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String code;
    private String name;
    private String description;
    private IEnumWorkflowCategory.Types category;
    private IEnumWorkflow.Types type;
    private List<WorkflowStateDto> workflowStates;
    private List<WorkflowTransitionDto> workflowTransitions;
}
