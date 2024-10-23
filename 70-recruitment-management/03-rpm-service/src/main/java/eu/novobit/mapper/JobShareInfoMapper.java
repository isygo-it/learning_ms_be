package eu.novobit.mapper;

import eu.novobit.dto.data.JobShareInfoDto;
import eu.novobit.model.JobShareInfo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Job share info mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface JobShareInfoMapper extends EntityMapper<JobShareInfo, JobShareInfoDto> {
}
