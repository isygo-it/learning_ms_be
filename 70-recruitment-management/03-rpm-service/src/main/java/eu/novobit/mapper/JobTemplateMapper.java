package eu.novobit.mapper;

import eu.novobit.dto.data.JobTemplateDto;
import eu.novobit.model.JobTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Job template mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface JobTemplateMapper extends EntityMapper<JobTemplate, JobTemplateDto> {
}

