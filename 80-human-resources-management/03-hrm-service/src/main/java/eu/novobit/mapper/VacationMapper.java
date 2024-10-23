package eu.novobit.mapper;

import eu.novobit.dto.data.VacationDto;
import eu.novobit.model.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Cin mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface VacationMapper extends EntityMapper<Vacation, VacationDto> {

}
