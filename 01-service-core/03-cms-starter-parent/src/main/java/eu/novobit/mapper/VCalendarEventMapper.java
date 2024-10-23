package eu.novobit.mapper;

import eu.novobit.dto.data.VCalendarEventDto;
import eu.novobit.model.VCalendarEvent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface V calendar event mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface VCalendarEventMapper extends EntityMapper<VCalendarEvent, VCalendarEventDto> {

}
