package eu.novobit.mapper;

import eu.novobit.dto.data.VCalendarDto;
import eu.novobit.model.VCalendar;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface V calendar mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface VCalendarMapper extends EntityMapper<VCalendar, VCalendarDto> {

}
