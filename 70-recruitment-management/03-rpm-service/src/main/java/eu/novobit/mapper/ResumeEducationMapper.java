package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeEducationDto;
import eu.novobit.model.ResumeEducation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume education mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeEducationMapper extends EntityMapper<ResumeEducation, ResumeEducationDto> {
}
