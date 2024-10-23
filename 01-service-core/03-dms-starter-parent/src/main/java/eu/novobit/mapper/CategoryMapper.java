package eu.novobit.mapper;


import eu.novobit.dto.data.CategoryDto;
import eu.novobit.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Category mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CategoryMapper extends EntityMapper<Category, CategoryDto> {
}
