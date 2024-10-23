package eu.novobit.api;

import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.enumerations.IEnumFileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * The interface File converter api.
 */
public interface FileConverterApi {

    /**
     * Convert pdf response entity.
     *
     * @param requestContext the request context
     * @param targetFormat   the target format
     * @param file           the file
     * @return the response entity
     */
    @Operation(summary = "convertPdf Api",
            description = "convertPdf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Resource> convertPdf(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                        @RequestParam(name = RestApiContants.target) IEnumFileType.Types targetFormat,
                                        @Valid @RequestBody MultipartFile file);

    /**
     * Convert html response entity.
     *
     * @param requestContext the request context
     * @param targetFormat   the target format
     * @param file           the file
     * @return the response entity
     */
    @Operation(summary = "convertHtml Api",
            description = "convertHtml")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/html", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Resource> convertHtml(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @RequestParam(name = RestApiContants.target) IEnumFileType.Types targetFormat,
                                         @Valid @RequestBody MultipartFile file);
}
