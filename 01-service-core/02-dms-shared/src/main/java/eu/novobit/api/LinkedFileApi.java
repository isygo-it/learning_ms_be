package eu.novobit.api;

import eu.novobit.com.rest.api.ILinkedFileApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.RequestContextDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * The interface Linked file api.
 */
public interface LinkedFileApi extends ILinkedFileApi<LinkedFileDto> {

    /**
     * Search by tags response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param tags           the tags
     * @return the response entity
     */
    @Operation(summary = "searchByTags Api",
            description = "searchByTags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/searchByTags")
    ResponseEntity<List<LinkedFileDto>> searchByTags(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                     @RequestParam(name = RestApiContants.tags) String tags);


    /**
     * Search by original name response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param originalName   the original name
     * @return the response entity
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/searchByOriginalName")
    ResponseEntity<LinkedFileDto> searchByOriginalName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                       @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                       @RequestParam(name = RestApiContants.originalName) String originalName);

    /**
     * Rename file response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param oldCode        the old code
     * @param newCode        the new code
     * @return the response entity
     */
    @Operation(summary = "renameFile Api",
            description = "renameFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/renameFile")
    ResponseEntity<LinkedFileDto> renameFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                             @RequestParam(name = RestApiContants.oldCode) String oldCode,
                                             @RequestParam(name = RestApiContants.newCode) String newCode);

    /**
     * Search by categories response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param categories     the categories
     * @return the response entity
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/searchByCategories")
    ResponseEntity<List<LinkedFileDto>> searchByCategories(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                           @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                           @RequestParam(name = RestApiContants.categories) String categories);

    /**
     * Gets directory tree.
     *
     * @param requestContext the request context
     * @return the directory tree
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/getDirectoryTree")
    ResponseEntity<String> getDirectoryTree(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);
}
