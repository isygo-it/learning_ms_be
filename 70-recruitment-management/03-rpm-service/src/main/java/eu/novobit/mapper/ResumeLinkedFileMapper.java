package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeLinkedFileDto;
import eu.novobit.model.ResumeLinkedFile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume linked file mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeLinkedFileMapper extends EntityMapper<ResumeLinkedFile, ResumeLinkedFileDto> {
}
