package eu.novobit.mapper;

import eu.novobit.dto.data.StorageConfigDto;
import eu.novobit.model.StorageConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Storage config mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface StorageConfigMapper extends EntityMapper<StorageConfig, StorageConfigDto> {

}
