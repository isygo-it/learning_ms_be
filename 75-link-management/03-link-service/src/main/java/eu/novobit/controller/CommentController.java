package eu.novobit.controller;


import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.CommentControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.CommentDto;
import eu.novobit.exception.handler.LinkExceptionHandler;
import eu.novobit.mapper.CommentMapper;
import eu.novobit.model.Comment;
import eu.novobit.service.ICommentService;
import eu.novobit.service.IPostService;
import eu.novobit.service.impl.CommentService;
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

@Slf4j
@Validated
@RestController
@CtrlDef(handler = LinkExceptionHandler.class, mapper = CommentMapper.class, service = CommentService.class)
@RequestMapping(value = "/api/private/comment")
public class CommentController extends MappedCrudController<Comment, CommentDto, CommentDto, CommentService> implements CommentControllerApi {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ICommentService commentService;
    @Autowired
    IPostService postService;

    @Operation(summary = "createLikeComment Api",
            description = "createLikeComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/like")
    public ResponseEntity<CommentDto> createLikeComment(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                        @RequestParam(name = RestApiContants.ID) Long commentId,
                                                        @RequestParam(name = RestApiContants.accountCode) String accountCode
    ) {
        try {
            final Comment comment = commentService.findById(commentId);
            final List<String> accountList = comment.getUsersAccountCode();
            accountList.add(accountCode);
            comment.setUsersAccountCode(accountList);
            commentService.update(comment);
            final CommentDto commentDto = commentMapper.entityToDto(comment);
            return ResponseFactory.ResponseOk(commentDto);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Operation(summary = "getListUsersLikedComment Api",
            description = "getListUsersLikedComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/like/{commentId}")
    public ResponseEntity<List<String>> getLikedCommentByCommentId(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                   @PathVariable Long commentId) {
        try {
            return ResponseFactory.ResponseOk(commentService.findById(commentId).getUsersAccountCode());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    @Operation(summary = "createDislikeComment Api",
            description = "createDislikeComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/dislike")
    public ResponseEntity<CommentDto> createDislikeComment(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                           @RequestParam(name = RestApiContants.ID) Long commentId,
                                                           @RequestParam(name = RestApiContants.accountCode) String accountCode
    ) {
        try {
            final Comment comment = commentService.findById(commentId);
            final List<String> accountList = comment.getUsersAccountCode();
            accountList.remove(accountCode);
            comment.setUsersAccountCode(accountList);
            commentService.update(comment);
            final CommentDto commentDto = commentMapper.entityToDto(comment);
            return ResponseFactory.ResponseOk(commentDto);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }

    }

}
