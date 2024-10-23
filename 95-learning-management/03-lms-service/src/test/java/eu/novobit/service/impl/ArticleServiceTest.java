package eu.novobit.service.impl;

import eu.novobit.dto.data.ArticleDto;
import eu.novobit.dto.data.AuthorDto;
import eu.novobit.mapper.AuthorMapper;
import eu.novobit.mapper.TopicMapper;
import eu.novobit.model.Author;
import eu.novobit.model.Topic;
import eu.novobit.repository.TopicRepository;
import eu.novobit.repository.UserArticleVisitRepository;
import eu.novobit.service.IAuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleServiceMock;

    @Mock
    private UserArticleVisitRepository userArticleVisitRepositoryMock;

    @Mock
    private IAuthorService authorServiceMock;

    @Mock
    private AuthorMapper authorMapperMock;

    @Mock
    private TopicMapper topicMapperMock;

    @Mock
    private TopicRepository topicRepositoryMock;


    @Test
    void testGetVisitedArticleIds() {
        // when
        List<Long> articlesIds = new ArrayList<>();
        articlesIds.add(5L);
        Mockito.when(userArticleVisitRepositoryMock.findVisitedArticleIdsByUserId(Mockito.anyLong())).thenReturn(articlesIds);

        // then
        List<Long> result = articleServiceMock.getVisitedArticleIds(1L);

        // verify
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testBeforeCreate_authorIdNull_TopicIdsNull() throws Exception {
        // when
        ArticleDto dto = new ArticleDto();
        dto.setAuthorID(null);
        dto.setTopicIds(null);

        // then
        ArticleDto result = articleServiceMock.beforeCreate(dto);

        // verify
        assertNull(result.getAuthor());
        assertNull(result.getTopics());
        Mockito.verify(authorServiceMock, Mockito.never()).findById(Mockito.anyLong());
        Mockito.verify(authorMapperMock, Mockito.never()).entityToDto(Mockito.any());
        Mockito.verify(topicRepositoryMock, Mockito.never()).findAllByIdIn(Mockito.any());
        Mockito.verify(topicMapperMock, Mockito.never()).entityToDto(Mockito.any());

    }

    @Test
    void testBeforeCreate_authorIdNotNull_TopicIdsNull() throws Exception {
        // when
        ArticleDto dto = new ArticleDto();
        dto.setAuthorID(1L);
        dto.setTopicIds(null);
        Mockito.when(authorServiceMock.findById(Mockito.anyLong())).thenReturn(new Author());
        Mockito.when(authorMapperMock.entityToDto(Mockito.any())).thenReturn(new AuthorDto());

        // then
        ArticleDto result = articleServiceMock.beforeCreate(dto);

        // verify
        assertNotNull(result.getAuthor());

        assertNull(result.getTopics());
        Mockito.verify(authorServiceMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(authorMapperMock, Mockito.times(1)).entityToDto(Mockito.any());
        Mockito.verify(topicRepositoryMock, Mockito.never()).findAllByIdIn(Mockito.any());
        Mockito.verify(topicMapperMock, Mockito.never()).entityToDto(Mockito.any());

    }

    @Test
    void testBeforeCreate_authorIdNotNull_TopicIdsNotNull() throws Exception {
        // when
        ArticleDto dto = new ArticleDto();
        dto.setAuthorID(1L);
        dto.setTopicIds(null);
        Mockito.when(authorServiceMock.findById(Mockito.anyLong())).thenReturn(new Author());
        Mockito.when(authorMapperMock.entityToDto(Mockito.any())).thenReturn(new AuthorDto());
        Long topicIds[] = new Long[]{10L, 20L, 15L};
        dto.setTopicIds(topicIds);
        List<Topic> topics = new ArrayList<>();
        for (int i = 0; i < topicIds.length; i++) {
            topics.add(new Topic());
        }
        Mockito.when(topicRepositoryMock.findAllByIdIn(topicIds)).thenReturn(topics);

        // then
        ArticleDto result = articleServiceMock.beforeCreate(dto);

        // verify
        assertNotNull(result.getAuthor());
        assertNotNull(result.getTopics());
        assertEquals(3, result.getTopics().size());
        Mockito.verify(authorServiceMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(authorMapperMock, Mockito.times(1)).entityToDto(Mockito.any());
        Mockito.verify(topicRepositoryMock, Mockito.times(1)).findAllByIdIn(Mockito.any());
        Mockito.verify(topicMapperMock, Mockito.times(3)).entityToDto(Mockito.any());

    }
}