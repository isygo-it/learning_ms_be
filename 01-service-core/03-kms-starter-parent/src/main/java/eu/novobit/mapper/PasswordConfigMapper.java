package eu.novobit.mapper;

import eu.novobit.dto.data.PasswordConfigDto;
import eu.novobit.model.PasswordConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Password config mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PasswordConfigMapper extends EntityMapper<PasswordConfig, PasswordConfigDto> {

}
