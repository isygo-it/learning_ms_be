package eu.novobit.com.rest.api;


import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.IDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.dto.RequestContextDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The interface Upload image api.
 *
 * @param <D> the type parameter
 */
public interface IUploadImageApi<D extends IDto & IImageUploadDto> {


    /**
     * Download image response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "Download the image by linked object identifier",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/image/download/{id}")
    ResponseEntity<Resource> downloadImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                           @PathVariable(name = RestApiContants.ID) Long id) throws IOException;

    /**
     * Create with image response entity.
     *
     * @param requestContext the request context
     * @param file           the file
     * @param dto            the dto
     * @return the response entity
     */
    @Operation(summary = "Create a new object and upload linked image file",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createWithImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @Valid @RequestBody MultipartFile file, D dto);

    /**
     * Update with image response entity.
     *
     * @param requestContext the request context
     * @param file           the file
     * @param dto            the dto
     * @return the response entity
     */
    @Operation(summary = "Upload a new image file and update the linked object",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateWithImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @Valid @RequestBody MultipartFile file, D dto);

    /**
     * Upload image response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param file           the file
     * @return the response entity
     */
    @Operation(summary = "Upload a new image file and link it to an object with object identifier",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/image/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<D> uploadImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                  @PathVariable(name = RestApiContants.ID) Long id,
                                  @Valid @RequestBody MultipartFile file);
}
