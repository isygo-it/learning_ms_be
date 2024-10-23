package eu.novobit.mapper;

import eu.novobit.dto.data.WorkflowTransitionDto;
import eu.novobit.model.WorkflowTransition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Workflow transition mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface WorkflowTransitionMapper extends EntityMapper<WorkflowTransition, WorkflowTransitionDto> {
}
