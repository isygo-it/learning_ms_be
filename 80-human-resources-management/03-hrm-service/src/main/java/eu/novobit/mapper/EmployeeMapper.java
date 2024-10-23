package eu.novobit.mapper;

import eu.novobit.dto.data.EmployeeDto;
import eu.novobit.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Employee mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<Employee, EmployeeDto> {

}
