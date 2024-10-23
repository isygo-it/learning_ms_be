package eu.novobit.mapper;


import eu.novobit.dto.data.PropertyDto;
import eu.novobit.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Property mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PropertyMapper extends EntityMapper<Property, PropertyDto> {
}
