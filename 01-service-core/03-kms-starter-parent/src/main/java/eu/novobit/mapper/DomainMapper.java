package eu.novobit.mapper;

import eu.novobit.dto.data.KmsDomainDto;
import eu.novobit.model.KmsDomain;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Domain mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface DomainMapper extends EntityMapper<KmsDomain, KmsDomainDto> {

}
