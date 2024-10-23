package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.BucketDto;
import eu.novobit.dto.data.FileTagsDto;
import eu.novobit.enumerations.IEnumLogicalOpe;
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

import java.util.List;


/**
 * The interface Object storage controller api.
 */
public interface ObjectStorageControllerApi {

    /**
     * Upload response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param tags           the tags
     * @param file           the file
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
    @PostMapping(path = "/upload/{domain}/{bucketName}/{path}/{fileName}/{tags}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> upload(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                  @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                  @PathVariable(name = RestApiContants.bucketName) String bucketName,
                                  @PathVariable(name = RestApiContants.path) String path,
                                  @PathVariable(name = RestApiContants.fileName) String fileName,
                                  @PathVariable(name = RestApiContants.tags) List<String> tags,
                                  @RequestPart("file") MultipartFile file);

    /**
     * Download resource.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param versionID      the version id
     * @return the resource
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/download/{domain}/{bucketName}/{path}/{fileName}")
    Resource download(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                      @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                      @PathVariable(name = RestApiContants.bucketName) String bucketName,
                      @PathVariable(name = RestApiContants.path) String path,
                      @PathVariable(name = RestApiContants.fileName) String fileName,
                      @RequestParam(name = RestApiContants.versionID) String versionID);

    /**
     * Delete response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param file           the file
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
    @DeleteMapping(path = "/delete/{domain}/{bucketName}/{path}/{fileName}")
    ResponseEntity<Object> delete(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                  @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                  @PathVariable(name = RestApiContants.bucketName) String bucketName,
                                  @PathVariable(name = RestApiContants.path) String path,
                                  @PathVariable(name = RestApiContants.fileName) String fileName);


    /**
     * Gets objects.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @return the objects
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/getObjects/{domain}/{bucketName}")
    ResponseEntity<Object> getObjects(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                      @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                      @PathVariable(name = RestApiContants.bucketName) String bucketName);


    /**
     * Filter objects response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param tags           the tags
     * @param condition      the condition
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
    @GetMapping(path = "/filterObjects/{domain}/{bucketName}/{tags}/{condition}")
    ResponseEntity<Object> filterObjects(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                         @PathVariable(name = RestApiContants.bucketName) String bucketName,
                                         @PathVariable(name = RestApiContants.tags) String tags,
                                         @PathVariable(name = RestApiContants.condition) IEnumLogicalOpe.Types condition);

    /**
     * Update tags response entity.
     *
     * @param fileTags the file tags
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
    @PutMapping(path = "/updateTags")
    ResponseEntity<Object> updateTags(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                      @Valid @RequestBody FileTagsDto fileTags);

    /**
     * Delete objects response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param files          the files
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
    @DeleteMapping(path = "/deleteObjects/{domain}/{bucketName}/{files}")
    ResponseEntity<Object> deleteObjects(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                         @PathVariable(name = RestApiContants.bucketName) String bucketName,
                                         @PathVariable(name = RestApiContants.files) String files);

    /**
     * Save bucket response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
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
    @PostMapping(path = "/saveBucket/{domain}/{bucketName}")
    ResponseEntity<Object> saveBucket(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                      @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                      @PathVariable(name = RestApiContants.bucketName) String bucketName);

    /**
     * Sets versioning bucket.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
     * @param status         the status
     * @return the versioning bucket
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/setVersioningBucket/{domain}/{bucketName}/{status}")
    ResponseEntity<Object> setVersioningBucket(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                               @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                               @PathVariable(name = RestApiContants.bucketName) String bucketName,
                                               @PathVariable(name = RestApiContants.status) boolean status);


    /**
     * Gets buckets.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @return the buckets
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/getBuckets/{domain}")
    ResponseEntity<List<BucketDto>> getBuckets(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                               @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain);


    /**
     * Delete bucket response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param bucketName     the bucket name
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
    @DeleteMapping(path = "/deleteBucket/{domain}/{bucketName}")
    ResponseEntity<Object> deleteBucket(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                        @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                        @PathVariable(name = RestApiContants.bucketName) String bucketName);
}