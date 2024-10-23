package eu.novobit.repository;

        import eu.novobit.model.Article;
        import org.springframework.stereotype.Repository;


/**
 * The interface Article repository.
 */
@Repository
public interface ArticleRepository extends JpaPagingAndSortingSASCodifiableRepository<Article, Long> {

}