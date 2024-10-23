package eu.novobit.mapper;

import eu.novobit.dto.data.AccountDetailsDto;
import eu.novobit.model.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Account details mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AccountDetailsMapper extends EntityMapper<AccountDetails, AccountDetailsDto> {
}
