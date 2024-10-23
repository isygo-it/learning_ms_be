package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.LnkFileService;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudFileService;
import eu.novobit.config.AppProperties;
import eu.novobit.dto.data.ArticleDto;
import eu.novobit.mapper.AuthorMapper;
import eu.novobit.mapper.TopicMapper;
import eu.novobit.model.*;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.dms.DmsLinkedFileService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.ArticleRepository;
import eu.novobit.repository.TopicRepository;
import eu.novobit.repository.UserArticleVisitRepository;
import eu.novobit.service.IArticleService;
import eu.novobit.service.IAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Article service.
 */
@Slf4j
@Service
@Transactional
@LnkFileService(DmsLinkedFileService.class)
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = ArticleRepository.class)
public class ArticleService extends AbstractCrudFileService<Article, ArticleRepository> implements IArticleService {

    private final AppProperties appProperties;
    private final IAuthorService authorService;
    private final AuthorMapper authorMapper;
    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;
    private final UserArticleVisitRepository userArticleVisitRepository;
    private final ArticleRepository articleRepository;

    /**
     * Instantiates a new Article service.
     *
     * @param appProperties   the app properties
     * @param authorService
     * @param authorMapper
     * @param topicMapper
     * @param topicRepository
     */
    public ArticleService(AppProperties appProperties, IAuthorService authorService, AuthorMapper authorMapper, TopicMapper topicMapper, TopicRepository topicRepository, UserArticleVisitRepository userArticleVisitRepository, ArticleRepository articleRepository) {
        this.appProperties = appProperties;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.topicMapper = topicMapper;
        this.topicRepository = topicRepository;
        this.userArticleVisitRepository = userArticleVisitRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Article.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("ART")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    public List<Long> getVisitedArticleIds(Long userId) {
        return userArticleVisitRepository.findVisitedArticleIdsByUserId(userId);
    }

    @Override
    public void saveUserArticleVisit(Long userId, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + articleId));

        UserArticleVisit visit = new UserArticleVisit();
        visit.setUserId(userId); // Correctly set the userId field
        visit.setArticle(article);
        visit.setVisitedAt(LocalDateTime.now());

        userArticleVisitRepository.save(visit);
        log.info("Saved user visit for User ID: {} and Article ID: {}", userId, articleId);
    }

    @Override
    public ArticleDto beforeCreate(ArticleDto articleDto) throws Exception {
        log.info("Initial ArticleDto: {}", articleDto);

        if (articleDto.getAuthorID() != null) {
            Author author = authorService.findById(articleDto.getAuthorID());
            if (author != null) {
                articleDto.setAuthor(authorMapper.entityToDto(author));
            }
        }

        if (articleDto.getTopicIds() != null && articleDto.getTopicIds().length > 0) {
            List<Topic> topics = topicRepository.findAllByIdIn(articleDto.getTopicIds());
            if (CollectionUtils.isEmpty(articleDto.getTopics())) {
                articleDto.setTopics(new ArrayList<>());
            }
            topics.forEach(topic -> articleDto.getTopics().add(topicMapper.entityToDto(topic)));
        }
        return articleDto;
    }

}