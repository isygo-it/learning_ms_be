package eu.novobit.mapper;

import eu.novobit.dto.request.UpdateAccountRequestDto;
import eu.novobit.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Account mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AccountMapper extends EntityMapper<Account, UpdateAccountRequestDto> {

}
