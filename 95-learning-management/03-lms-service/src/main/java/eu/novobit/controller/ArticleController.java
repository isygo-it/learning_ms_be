package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.ArticleControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.ArticleDto;
import eu.novobit.exception.handler.LmsExceptionHandler;
import eu.novobit.mapper.ArticleMapper;
import eu.novobit.mapper.AuthorMapper;
import eu.novobit.mapper.TopicMapper;
import eu.novobit.model.Article;
import eu.novobit.model.Author;
import eu.novobit.model.Topic;
import eu.novobit.repository.TopicRepository;
import eu.novobit.service.IAuthorService;
import eu.novobit.service.impl.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Article controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = LmsExceptionHandler.class, mapper = ArticleMapper.class, service = ArticleService.class)
@RequestMapping(value = "/api/private/article")
public class ArticleController extends MappedCrudController<Article, ArticleDto, ArticleDto, ArticleService> implements ArticleControllerApi {

    @Autowired
    private IAuthorService authorService;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;



    @Override
    public ArticleDto beforeCreate(ArticleDto articleDto) {
        Author author = authorService.findById(articleDto.getAuthorID());
        articleDto.setAuthor(authorMapper.entityToDto(author));
        return super.beforeCreate(articleDto);
    }

    @Override
    public ArticleDto beforeUpdate(Long id, ArticleDto articleDto) {
        Author author = authorService.findById(articleDto.getAuthorID());
        articleDto.setAuthor(authorMapper.entityToDto(author));

        if (articleDto.getTopicIds() != null && articleDto.getTopicIds().length > 0) {
            List<Topic> topics = topicRepository.findAllByIdIn(articleDto.getTopicIds());
            if (CollectionUtils.isEmpty(articleDto.getTopics())) {
                articleDto.setTopics(new ArrayList<>());
            }
            topics.forEach(topic -> articleDto.getTopics().add(topicMapper.entityToDto(topic)));
        }

        return super.beforeUpdate(id, articleDto);
    }

}
