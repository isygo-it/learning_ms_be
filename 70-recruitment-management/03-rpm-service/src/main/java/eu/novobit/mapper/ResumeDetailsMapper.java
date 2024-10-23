package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeDetailsDto;
import eu.novobit.model.ResumeDetails;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume details mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeDetailsMapper extends EntityMapper<ResumeDetails, ResumeDetailsDto> {
}
