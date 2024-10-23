package eu.novobit.mapper;

import eu.novobit.dto.data.EmployeeLinkedFileDto;
import eu.novobit.model.EmployeeLinkedFile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Employee linked file mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface EmployeeLinkedFileMapper extends EntityMapper<EmployeeLinkedFile, EmployeeLinkedFileDto> {
}
