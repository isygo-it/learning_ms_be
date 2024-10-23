package eu.novobit.mapper;

import eu.novobit.dto.data.InsuranceSecurityDto;
import eu.novobit.model.InsuranceSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Insurance security mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface InsuranceSecurityMapper extends EntityMapper<InsuranceSecurity, InsuranceSecurityDto> {

}
