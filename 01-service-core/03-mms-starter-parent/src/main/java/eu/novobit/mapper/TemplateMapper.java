package eu.novobit.mapper;

import eu.novobit.dto.data.TemplateDto;
import eu.novobit.model.Template;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Template mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface TemplateMapper extends EntityMapper<Template, TemplateDto> {
}
