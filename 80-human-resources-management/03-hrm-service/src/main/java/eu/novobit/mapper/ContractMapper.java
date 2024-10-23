package eu.novobit.mapper;

import eu.novobit.dto.data.ContractDto;
import eu.novobit.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Contract mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ContractMapper extends EntityMapper<Contract, ContractDto> {

}
