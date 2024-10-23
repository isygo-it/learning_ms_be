package eu.novobit.mapper;

import eu.novobit.dto.data.TokenConfigDto;
import eu.novobit.model.TokenConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Token config mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface TokenConfigMapper extends EntityMapper<TokenConfig, TokenConfigDto> {

}
