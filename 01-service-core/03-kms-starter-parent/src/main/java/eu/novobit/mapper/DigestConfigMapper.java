package eu.novobit.mapper;

import eu.novobit.dto.data.DigestConfigDto;
import eu.novobit.model.DigestConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Digest config mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface DigestConfigMapper extends EntityMapper<DigestConfig, DigestConfigDto> {

}
