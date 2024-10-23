package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeSkillsDto;
import eu.novobit.model.ResumeSkills;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume skills mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeSkillsMapper extends EntityMapper<ResumeSkills, ResumeSkillsDto> {
}
