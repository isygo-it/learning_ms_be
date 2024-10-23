package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.CandidateQuizAnswerDto;
import eu.novobit.dto.data.CandidateQuizDto;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.dto.data.QuizReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The interface Candidate quiz controller api.
 */
public interface CandidateQuizControllerApi extends IMappedCrudApi<CandidateQuizDto, CandidateQuizDto> {

    /**
     * Gets candidate quiz.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the candidate quiz
     */
    @Operation(summary = "getCandidateQuiz Api",
            description = "getCandidateQuiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizDto.class))})
    })
    @GetMapping(path = "/copy")
    ResponseEntity<QuizDto> getCandidateQuiz(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                             @RequestParam(name = RestApiContants.accountCode) String accountCode);

    /**
     * Submit candidate quiz response entity.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the response entity
     */
    @Operation(summary = "submitCandidateQuiz Api",
            description = "submitCandidateQuiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))})
    })
    @PutMapping(path = "/submit")
    ResponseEntity<Boolean> submitCandidateQuiz(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                @RequestParam(name = RestApiContants.accountCode) String accountCode);

    /**
     * Start candidate quiz response entity.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the response entity
     */
    @Operation(summary = "startCandidateQuiz Api",
            description = "startCandidateQuiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))})
    })
    @PostMapping(path = "/start")
    ResponseEntity<Long> startCandidateQuiz(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                            @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                            @RequestParam(name = RestApiContants.accountCode) String accountCode);


    /**
     * Submit candidate quiz answer response entity.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answer      the answer
     * @return the response entity
     */
    @Operation(summary = "submitCandidateQuizAnswer Api",
            description = "submitCandidateQuizAnswer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))})
    })
    @PutMapping(path = "/answer/submit")
    ResponseEntity<Boolean> submitCandidateQuizAnswer(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                      @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                      @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                      @Valid @RequestBody CandidateQuizAnswerDto answer);

    /**
     * Submit candidate quiz answer list response entity.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answers     the answers
     * @return the response entity
     */
    @Operation(summary = "submitCandidateQuizAnswerList Api",
            description = "submitCandidateQuizAnswerList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))})
    })
    @PutMapping(path = "/answer/list/submit")
    ResponseEntity<Boolean> submitCandidateQuizAnswerList(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                          @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                          @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                          @Valid @RequestBody List<CandidateQuizAnswerDto> answers);

    /**
     * Start candidate quiz answer response entity.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answer      the answer
     * @return the response entity
     */
    @Operation(summary = "startCandidateQuizAnswer Api",
            description = "startCandidateQuizAnswer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))})
    })
    @PostMapping(path = "/answer/start")
    ResponseEntity<Boolean> startCandidateQuizAnswer(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                     @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                     @Valid @RequestBody CandidateQuizAnswerDto answer);

    /**
     * Gets complete answer.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the complete answer
     */
    @Operation(summary = "getCompleteAnswer Api",
            description = "getCompleteAnswer: Quiz, sections, questions and answers with score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizDto.class))})
    })
    @GetMapping(path = "/complete")
    ResponseEntity<QuizDto> getCompleteAnswer(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                              @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                              @RequestParam(name = RestApiContants.accountCode) String accountCode);

    /**
     * Gets complete answer.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the complete answer
     */
    @Operation(summary = "getCompleteAnswerClean Api",
            description = "getCompleteAnswerClean: Quiz, sections, questions and answers with score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizDto.class))})
    })
    @GetMapping(path = "/complete/clean")
    ResponseEntity<QuizDto> getCompleteAnswerClean(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                   @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                   @RequestParam(name = RestApiContants.accountCode) String accountCode);

    /**
     * Gets report answer.
     *
     * @param requestContext the request context
     * @param quizCode       the quiz code
     * @param accountCode    the account code
     * @return the report answer
     */
    @Operation(summary = "getReportAnswer Api",
            description = "getReportAnswer : Quiz and sections with score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizReportDto.class))})
    })
    @GetMapping(path = "/report")
    ResponseEntity<QuizReportDto> getReportAnswer(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                  @RequestParam(name = RestApiContants.quizCode) String quizCode,
                                                  @RequestParam(name = RestApiContants.accountCode) String accountCode);

    /**
     * Gets by candidate and tags.
     *
     * @param requestContext the request context
     * @param accountCode    the account code
     * @param tags           the tags
     * @return the by candidate and tags
     */
    @Operation(summary = "getByCandidateAndTags Api",
            description = "getByCandidateAndTags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuizReportDto.class))})
    })
    @GetMapping(path = "/tags")
    ResponseEntity<List<QuizReportDto>> getByCandidateAndTags(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                              @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                              @RequestParam(name = RestApiContants.tags) List<String> tags);

    @GetMapping(path = "/total")
    ResponseEntity<Integer> getCountRealizedTestByAccount(@RequestParam(name = RestApiContants.ACCOUNT_CODE) String accountCode);
}
