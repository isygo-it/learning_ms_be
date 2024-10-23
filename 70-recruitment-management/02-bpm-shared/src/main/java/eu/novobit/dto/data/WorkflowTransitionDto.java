package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Workflow transition dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WorkflowTransitionDto extends AbstractAuditableDto<Long> {

    private String code;
    private String fromCode;
    private String toCode;
    private String transitionService;
    private Boolean notify;
    private Boolean bidirectional;
    private String[] watchers;
    private String[] itemTypes;
}
