package eu.novobit.mapper;

import eu.novobit.dto.data.WorkflowDto;
import eu.novobit.model.Workflow;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Workflow mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface WorkflowMapper extends EntityMapper<Workflow, WorkflowDto> {
}
