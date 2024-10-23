package eu.novobit.mapper;

import eu.novobit.dto.data.CinDto;
import eu.novobit.model.Cin;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Cin mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CinMapper extends EntityMapper<Cin, CinDto> {

}
