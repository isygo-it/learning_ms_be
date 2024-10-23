package eu.novobit.service;

        import eu.novobit.com.rest.service.ICrudFileService;
        import eu.novobit.com.rest.service.ICrudServiceMethods;
        import eu.novobit.dto.data.ArticleDto;
        import eu.novobit.model.Article;

        import java.util.List;

/**
 * The interface Article service.
 */
public interface IArticleService extends ICrudServiceMethods<Article> , ICrudFileService<Article>   {
        ArticleDto beforeCreate(ArticleDto articleDto) throws Exception;
        List<Long> getVisitedArticleIds(Long userId);
        void saveUserArticleVisit(Long userId, Long articleId);  // New method


}
