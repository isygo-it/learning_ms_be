package eu.novobit.mapper;

import eu.novobit.dto.data.JobLinkedFileDto;
import eu.novobit.model.JobLinkedFile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Job linked file mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface JobLinkedFileMapper extends EntityMapper<JobLinkedFile, JobLinkedFileDto> {
}
