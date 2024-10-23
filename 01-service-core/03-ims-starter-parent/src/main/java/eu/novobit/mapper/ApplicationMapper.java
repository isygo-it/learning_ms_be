package eu.novobit.mapper;

import eu.novobit.dto.data.ApplicationDto;
import eu.novobit.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Application mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ApplicationMapper extends EntityMapper<Application, ApplicationDto> {
}
