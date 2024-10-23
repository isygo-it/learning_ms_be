package eu.novobit.mapper;

import eu.novobit.dto.data.SenderConfigDto;
import eu.novobit.model.SenderConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Sender config mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface SenderConfigMapper extends EntityMapper<SenderConfig, SenderConfigDto> {

}
