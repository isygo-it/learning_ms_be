package eu.novobit.com.rest.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.IFileUploadDto;
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

/**
 * The interface Upload file api.
 *
 * @param <D> the type parameter
 */
public interface IUploadFileApi<D extends IFileUploadDto> {

    /**
     * Download file response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param filename       the filename
     * @param version        the version
     * @return the response entity
     */
    @Operation(summary = "Download a file by domain, file name and version",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/file/download")
    ResponseEntity<Resource> downloadFile(
            @RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
            @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
            @RequestParam(name = RestApiContants.filename) String filename,
            @RequestParam(name = RestApiContants.version) Long version);

    /**
     * Create with file response entity.
     *
     * @param requestContext the request context
     * @param fileUpload     the file upload
     * @return the response entity
     */
    @Operation(summary = "Create a new object and upload linked file",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<D> createWithFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                     //@RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                     @ModelAttribute(RestApiContants.fileUpload) D fileUpload);

    /**
     * Update with file response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param fileUpload     the file upload
     * @return the response entity
     */
    @Operation(summary = "Upload a new file and update the linked object",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<D> updateWithFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                     @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                     @ModelAttribute(RestApiContants.fileUpload) D fileUpload);

    /**
     * Update with file response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param id             the id
     * @param fileUpload     the file upload
     * @return the response entity
     */
    @Operation(summary = "updateWithFile Api",
            description = "updateWithFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/update/{domain}/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<D> updateWithFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                            @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                            @PathVariable(name = RestApiContants.ID) Long id,
                                            @ModelAttribute(RestApiContants.fileUpload) D fileUpload);

    /**
     * Upload file response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param file           the file
     * @return the response entity
     */
    @Operation(summary = "Upload a new file and link it to an object with object identifier",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/file/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<D> uploadFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                 @PathVariable(name = RestApiContants.ID) Long id,
                                 @Valid @RequestBody MultipartFile file);
}
