package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeCertificationDto;
import eu.novobit.model.ResumeCertification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume certification mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeCertificationMapper extends EntityMapper<ResumeCertification, ResumeCertificationDto> {
}
