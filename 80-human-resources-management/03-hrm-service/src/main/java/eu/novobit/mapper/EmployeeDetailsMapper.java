package eu.novobit.mapper;

import eu.novobit.dto.data.EmployeeDetailsDto;
import eu.novobit.model.EmployeeDetails;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Employee details mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface EmployeeDetailsMapper extends EntityMapper<EmployeeDetails, EmployeeDetailsDto> {

}
