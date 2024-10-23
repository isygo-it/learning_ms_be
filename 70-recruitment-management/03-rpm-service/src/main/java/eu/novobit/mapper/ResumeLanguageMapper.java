package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeLanguageDto;
import eu.novobit.model.ResumeLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume language mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeLanguageMapper extends EntityMapper<ResumeLanguage, ResumeLanguageDto> {
}
