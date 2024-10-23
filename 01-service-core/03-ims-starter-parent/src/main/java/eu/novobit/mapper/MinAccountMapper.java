package eu.novobit.mapper;

import eu.novobit.dto.data.MinAccountDto;
import eu.novobit.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Min account mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface MinAccountMapper extends EntityMapper<Account, MinAccountDto> {


}
