package eu.novobit.mapper;

import eu.novobit.dto.data.DomainDto;
import eu.novobit.model.Domain;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Domain mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface DomainMapper extends EntityMapper<Domain, DomainDto> {
}

