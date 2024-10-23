package eu.novobit.mapper;

import eu.novobit.dto.data.AccountDto;
import eu.novobit.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Account mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AccountMapper extends EntityMapper<Account, AccountDto> {

}
