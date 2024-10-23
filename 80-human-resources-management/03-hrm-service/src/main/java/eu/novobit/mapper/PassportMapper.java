package eu.novobit.mapper;

import eu.novobit.dto.data.PassportDto;
import eu.novobit.model.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Passport mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PassportMapper extends EntityMapper<Passport, PassportDto> {

}
