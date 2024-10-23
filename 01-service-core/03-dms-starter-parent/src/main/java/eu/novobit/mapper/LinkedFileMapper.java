package eu.novobit.mapper;


import eu.novobit.dto.LinkedFileDto;
import eu.novobit.model.LinkedFile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Linked file mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface LinkedFileMapper extends EntityMapper<LinkedFile, LinkedFileDto> {
}
