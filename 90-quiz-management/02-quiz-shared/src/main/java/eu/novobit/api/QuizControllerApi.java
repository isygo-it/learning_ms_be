package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.dto.data.QuizListDto;
import eu.novobit.dto.data.QuizQuestionDto;
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
import java.util.List;

/**
 * The interface Quiz controller api.
 */
public interface QuizControllerApi extends IMappedCrudApi<QuizDto, QuizDto> {

    /**
     * Download question image response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "Download question image by linked object identifier",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/question/image/download/{id}")
    ResponseEntity<Resource> downloadQuestionImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                   @PathVariable(name = RestApiContants.ID) Long id) throws IOException;

    /**
     * Upload question image response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param file           the file
     * @return the response entity
     */
    @Operation(summary = "Upload a new question image file and link it to an object with object identifier",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizQuestionDto.class))})
    })
    @PostMapping(path = "/question/image/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<QuizQuestionDto> uploadQuestionImage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                        @PathVariable(name = RestApiContants.ID) Long id,
                                                        @Valid @RequestBody MultipartFile file);


    /**
     * Gets quiz codes by category.
     *
     * @param requestContext the request context
     * @param category       the category
     * @return the quiz codes by category
     */
    @Operation(summary = "get Quiz Codes By Category",
            description = "get Quiz Codes By Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/category")
    ResponseEntity<List<QuizListDto>> getQuizCodesByCategory(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                             @RequestParam(name = RestApiContants.CATEGORY) String category);


    /**
     * Find by code response entity.
     *
     * @param requestContext the request context
     * @param code           the code
     * @return the response entity
     */
    @Operation(summary = "Find all objects with full data by object identifier (uses Full Dto)",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/code/{code}")
    ResponseEntity<QuizDto> findByCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                       @PathVariable(name = RestApiContants.code) String code);
}
