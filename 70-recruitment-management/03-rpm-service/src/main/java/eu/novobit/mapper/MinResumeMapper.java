package eu.novobit.mapper;

import eu.novobit.dto.data.MinResumeDto;
import eu.novobit.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Min resume mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface MinResumeMapper extends EntityMapper<Resume, MinResumeDto> {
}
