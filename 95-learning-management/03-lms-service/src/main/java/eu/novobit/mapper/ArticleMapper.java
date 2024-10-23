package eu.novobit.mapper;

        import eu.novobit.dto.data.ArticleDto;
        import eu.novobit.model.Article;
        import org.mapstruct.Mapper;
        import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Article mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ArticleMapper extends EntityMapper<Article, ArticleDto> {

}
