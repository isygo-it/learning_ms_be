package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.PostControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.PostDto;
import eu.novobit.exception.handler.LinkExceptionHandler;
import eu.novobit.mapper.PostMapper;
import eu.novobit.model.Post;
import eu.novobit.service.IPostService;
import eu.novobit.service.impl.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Post controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = LinkExceptionHandler.class, mapper = PostMapper.class, service = PostService.class)

@RequestMapping(value = "/api/private/post")
public class PostController extends MappedCrudController<Post, PostDto, PostDto, PostService> implements PostControllerApi {

    @Autowired
    IPostService postService;
    @Autowired
    PostMapper postMapper;


    @Operation(summary = "createLikePost Api",
            description = "createLikePost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/like")
    public ResponseEntity<PostDto> createLikePost(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                  @RequestParam(name = RestApiContants.ID) Long postId,
                                                  @RequestParam(name = RestApiContants.accountCode) String accountCode
    ) {
        try {
            final Post post = postService.findById(postId);
            final List<String> accountList = post.getUsersAccountCode();
            accountList.add(accountCode);
            post.setUsersAccountCode(accountList);
            postService.update(post);
            PostDto postDto = postMapper.entityToDto(post);
            return ResponseFactory.ResponseOk(postDto);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }

    }


    @Operation(summary = "getListUsersLikedPost Api",
            description = "getListUsersLikedPost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/like/{postId}")
    public ResponseEntity<List<String>> getUsersLikedPostByPostId(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                  @PathVariable Long postId) {
        try {
            return ResponseFactory.ResponseOk(postService.findById(postId).getUsersAccountCode());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Operation(summary = "createDislikePost Api",
            description = "createDislikePost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/dislike")
    public ResponseEntity<PostDto> createDislikePost(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.ID) Long postId,
                                                     @RequestParam(name = RestApiContants.accountCode) String accountCode
    ) {
        try {
            final Post post = postService.findById(postId);
            final List<String> accountList = post.getUsersAccountCode();
            accountList.remove(accountCode);
            post.setUsersAccountCode(accountList);
            postService.update(post);
            PostDto postDto = postMapper.entityToDto(post);
            return ResponseFactory.ResponseOk(postDto);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }

    }


}
