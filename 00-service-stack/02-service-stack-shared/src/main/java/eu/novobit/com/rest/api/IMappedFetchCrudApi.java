package eu.novobit.com.rest.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

/**
 * The interface Mapped fetch crud api.
 *
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 */
public interface IMappedFetchCrudApi<MinD extends IDto, FullD extends MinD> {

    /**
     * Find all response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Find all objects with minimal data (uses Min Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "")
    public ResponseEntity<List<MinD>> findAll(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Find all response entity.
     *
     * @param requestContext the request context
     * @param page           the page
     * @param size           the size
     * @return the response entity
     */
    @Operation(summary = "Find all objects with minimal data by page (uses Min Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/{page}/{size}")
    public ResponseEntity<List<MinD>> findAll(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                              @PathVariable(name = RestApiContants.page) Integer page,
                                              @PathVariable(name = RestApiContants.size) Integer size);

    /**
     * Find all full response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Find all objects with full data (uses Full Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/full")
    public ResponseEntity<List<FullD>> findAllFull(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Find all full response entity.
     *
     * @param requestContext the request context
     * @param page           the page
     * @param size           the size
     * @return the response entity
     */
    @Operation(summary = "Find all objects with full data by page (uses Full Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/full/{page}/{size}")
    public ResponseEntity<List<FullD>> findAllFull(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                   @PathVariable(name = RestApiContants.page) Integer page,
                                                   @PathVariable(name = RestApiContants.size) Integer size);

    /**
     * Find by id response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @return the response entity
     */
    @Operation(summary = "Find object with full data by object identifier (uses Full Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<FullD> findById(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                          @PathVariable(name = RestApiContants.ID) Long id);

    /**
     * Gets count.
     *
     * @param requestContext the request context
     * @return the count
     */
    @Operation(summary = "Get objects count",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/count")
    public ResponseEntity<Long> getCount(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);
}
