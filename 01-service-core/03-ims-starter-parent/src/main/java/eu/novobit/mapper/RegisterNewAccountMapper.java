package eu.novobit.mapper;

import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.model.RegistredUser;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Register new account mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface RegisterNewAccountMapper extends EntityMapper<RegistredUser, RegisterNewAccountDto> {
}
