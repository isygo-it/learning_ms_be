
package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedFileController;
import eu.novobit.dto.data.ArticleDto;
import eu.novobit.exception.handler.LmsExceptionHandler;
import eu.novobit.mapper.ArticleMapper;
import eu.novobit.model.Article;
import eu.novobit.service.IArticleService;
import eu.novobit.service.impl.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Article file controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/article")
@CtrlDef(handler = LmsExceptionHandler.class, mapper = ArticleMapper.class, service = ArticleService.class)
public class ArticleFileController extends MappedFileController<Article, ArticleDto, ArticleDto, ArticleService>   {

    @Autowired
    private IArticleService articleService;

    @Override
    public ArticleDto beforeCreate(ArticleDto articleDto) throws Exception {
        return articleService.beforeCreate(articleDto);
    }


     @GetMapping("/visited/{userId}")
     public ResponseEntity<List<Long>> getVisitedArticles(@PathVariable Long userId) {
         List<Long> visitedArticleIds = articleService.getVisitedArticleIds(userId);
         return ResponseEntity.ok(visitedArticleIds);
     }

    @PostMapping("/visit/{userId}/{articleId}")
    public ResponseEntity<Void> saveUserVisit(@PathVariable Long userId, @PathVariable Long articleId) {
        articleService.saveUserArticleVisit(userId, articleId);
        return ResponseEntity.ok().build();
    }


}