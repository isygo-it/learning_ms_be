package eu.novobit.com.rest.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.IFileUploadDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * The interface Linked file api.
 *
 * @param <D> the type parameter
 */
public interface ILinkedFileApi<D extends IFileUploadDto> {

    /**
     * Upload response entity.
     *
     * @param linkedFile the linked file
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<LinkedFileResponseDto> upload(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                 @ModelAttribute("linkedFile") D linkedFile) throws IOException;

    /**
     * Download response entity.
     *
     * @param requestContext   the request context
     * @param domain           the domain
     * @param originalFileName the original file name
     * @param version          the version
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Resource> download(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                      @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                      @RequestParam(name = RestApiContants.filename) String originalFileName,
                                      @RequestParam(name = RestApiContants.version) Long version) throws IOException;

    /**
     * Delete file response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param code           the code
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
    @DeleteMapping(path = "/deleteFile")
    ResponseEntity<Boolean> deleteFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                       @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                       @RequestParam(name = RestApiContants.code) String code);

}
