package eu.novobit.mapper;

import eu.novobit.dto.data.ConnectionTrackingDto;
import eu.novobit.model.ConnectionTracking;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;


/**
 * The interface Connection tracking mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ConnectionTrackingMapper extends EntityMapper<ConnectionTracking, ConnectionTrackingDto> {

}
