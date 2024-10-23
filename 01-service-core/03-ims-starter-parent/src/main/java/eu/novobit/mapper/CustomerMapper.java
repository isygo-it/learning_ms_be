package eu.novobit.mapper;

import eu.novobit.dto.data.CustomerDto;
import eu.novobit.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Customer mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CustomerMapper extends EntityMapper<Customer, CustomerDto> {
}

