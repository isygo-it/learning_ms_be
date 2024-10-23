package eu.novobit.mapper;

import eu.novobit.dto.data.ResumeShareInfoDto;
import eu.novobit.model.ResumeShareInfo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Resume share info mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ResumeShareInfoMapper extends EntityMapper<ResumeShareInfo, ResumeShareInfoDto> {
}
