package eu.novobit.mapper;

import eu.novobit.dto.data.JobApplicationDto;
import eu.novobit.model.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Job application mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface JobApplicationMapper extends EntityMapper<JobApplication, JobApplicationDto> {
}
