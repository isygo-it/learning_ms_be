package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeDto;
import eu.novobit.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeMapper extends EntityMapper<Resume, ResumeDto> {
}
