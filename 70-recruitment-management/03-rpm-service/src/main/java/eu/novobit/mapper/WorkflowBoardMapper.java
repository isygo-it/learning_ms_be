package eu.novobit.mapper;

import eu.novobit.dto.data.WorkflowBoardDto;
import eu.novobit.model.WorkflowBoard;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Workflow board mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface WorkflowBoardMapper extends EntityMapper<WorkflowBoard, WorkflowBoardDto> {
}
