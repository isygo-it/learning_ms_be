package eu.novobit.mapper;

import eu.novobit.dto.data.TimelineDto;
import eu.novobit.model.Timeline;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Timeline mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface TimelineMapper extends EntityMapper<Timeline, TimelineDto> {
}
