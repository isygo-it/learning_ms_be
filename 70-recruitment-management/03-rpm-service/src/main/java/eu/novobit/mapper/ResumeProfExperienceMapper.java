package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeProfExperienceDto;
import eu.novobit.model.ResumeProfExperience;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume prof experience mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeProfExperienceMapper extends EntityMapper<ResumeProfExperience, ResumeProfExperienceDto> {
}
