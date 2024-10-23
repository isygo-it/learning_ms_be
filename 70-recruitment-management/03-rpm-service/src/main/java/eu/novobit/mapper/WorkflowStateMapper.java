package eu.novobit.mapper;

import eu.novobit.dto.data.WorkflowStateDto;
import eu.novobit.model.WorkflowState;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Workflow state mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface WorkflowStateMapper extends EntityMapper<WorkflowState, WorkflowStateDto> {
}
