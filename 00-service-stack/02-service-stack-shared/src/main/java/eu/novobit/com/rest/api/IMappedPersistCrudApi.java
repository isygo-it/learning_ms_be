package eu.novobit.com.rest.api;

import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.IDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Mapped persist crud api.
 *
 * @param <D> the type parameter
 */
public interface IMappedPersistCrudApi<D extends IDto> {

    /**
     * Create response entity.
     *
     * @param object the object
     * @return the response entity
     */
    @Operation(summary = "Create a new object",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<D> create(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                    @Valid @RequestBody D object);


    /**
     * Update response entity.
     *
     * @param id     the id
     * @param object the object
     * @return the response entity
     */
    @Operation(summary = "Update an existing object",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<D> update(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                    @PathVariable(name = RestApiContants.ID) Long id,
                                    @Valid @RequestBody D object);

    /**
     * Partial update response entity.
     *
     * @param id     the id
     * @param object the object
     * @return the response entity
     */
    @Operation(summary = "Partially update an existing object",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<D> partialUpdate(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                           @PathVariable(name = RestApiContants.ID) Long id,
                                           @Valid @RequestBody D object);
}
