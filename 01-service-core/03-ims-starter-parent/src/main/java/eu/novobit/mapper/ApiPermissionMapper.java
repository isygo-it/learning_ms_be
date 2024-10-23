package eu.novobit.mapper;

import eu.novobit.dto.ApiPermissionDto;
import eu.novobit.model.ApiPermission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Api permission mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ApiPermissionMapper extends EntityMapper<ApiPermission, ApiPermissionDto> {

}
