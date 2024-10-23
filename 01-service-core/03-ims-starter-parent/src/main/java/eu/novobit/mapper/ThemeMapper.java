package eu.novobit.mapper;

import eu.novobit.dto.data.ThemeDto;
import eu.novobit.model.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Theme mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ThemeMapper extends EntityMapper<Theme, ThemeDto> {
}
