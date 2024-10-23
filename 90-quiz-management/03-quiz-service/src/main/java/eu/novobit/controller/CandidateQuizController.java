package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.CandidateQuizControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.CandidateQuizAnswerDto;
import eu.novobit.dto.data.CandidateQuizDto;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.dto.data.QuizReportDto;
import eu.novobit.exception.handler.QuizExceptionHandler;
import eu.novobit.mapper.CandidateQuizAnswerMapper;
import eu.novobit.mapper.CandidateQuizMapper;
import eu.novobit.model.CandidateQuiz;
import eu.novobit.service.impl.CandidateQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Candidate quiz controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = QuizExceptionHandler.class, mapper = CandidateQuizMapper.class, service = CandidateQuizService.class)
@RequestMapping(value = "/api/private/candidate/quiz")
public class CandidateQuizController extends MappedCrudController<CandidateQuiz, CandidateQuizDto, CandidateQuizDto, CandidateQuizService>
        implements CandidateQuizControllerApi {

    @Autowired
    private CandidateQuizAnswerMapper candidateQuizAnswerMapper;

    @Override
    public ResponseEntity<QuizDto> getCandidateQuiz(RequestContextDto requestContext,
                                                    String quizCode, String accountCode) {
        try {
            return ResponseFactory.ResponseOk(crudService().getCandidateQuiz(quizCode, accountCode));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> submitCandidateQuiz(RequestContextDto requestContext,
                                                       String quizCode, String accountCode) {
        try {
            return ResponseFactory.ResponseOk(crudService().submitCandidateQuiz(quizCode, accountCode));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Long> startCandidateQuiz(RequestContextDto requestContext,
                                                   String quizCode, String accountCode) {
        try {
            return ResponseFactory.ResponseOk(crudService().startCandidateQuiz(quizCode, accountCode));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> submitCandidateQuizAnswer(//RequestContextDto requestContext,
                                                             String quizCode, String accountCode, CandidateQuizAnswerDto answer) {
        try {
            return ResponseFactory.ResponseOk(crudService().submitCandidateAnswer(quizCode, accountCode,
                    candidateQuizAnswerMapper.dtoToEntity(answer)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> submitCandidateQuizAnswerList(String quizCode, String accountCode, List<CandidateQuizAnswerDto> answers) {
        try {
            return ResponseFactory.ResponseOk(crudService().submitCandidateAnswers(quizCode, accountCode,
                    candidateQuizAnswerMapper.listDtoToEntity(answers)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> startCandidateQuizAnswer(//RequestContextDto requestContext,
                                                            String quizCode, String accountCode, CandidateQuizAnswerDto answer) {
        try {
            return ResponseFactory.ResponseOk(crudService().startCandidateAnswer(quizCode, accountCode,
                    candidateQuizAnswerMapper.dtoToEntity(answer)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<QuizDto> getCompleteAnswer(RequestContextDto requestContext,
                                                     String quizCode, String accountCode) {
        try {
            QuizDto completeAnswer = crudService().getCompleteAnswer(quizCode, accountCode);
            if (completeAnswer != null) {
                return ResponseFactory.ResponseOk(completeAnswer);
            } else {
                return ResponseFactory.ResponseNoContent();
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<QuizDto> getCompleteAnswerClean(RequestContextDto requestContext,
                                                          String quizCode, String accountCode) {
        try {
            QuizDto completeAnswer = crudService().getCompleteAnswerClean(quizCode, accountCode);
            if (completeAnswer != null) {
                return ResponseFactory.ResponseOk(completeAnswer);
            } else {
                return ResponseFactory.ResponseNoContent();
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<QuizReportDto> getReportAnswer(RequestContextDto requestContext,
                                                         String quizCode, String accountCode) {
        try {
            QuizReportDto quizReport = crudService().getReport(quizCode, accountCode);
            if (quizReport != null) {
                return ResponseFactory.ResponseOk(quizReport);
            } else {
                return ResponseFactory.ResponseNoContent();
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<QuizReportDto>> getByCandidateAndTags(RequestContextDto requestContext,
                                                                     String accountCode, List<String> tags) {
        try {
            List<QuizReportDto> list = crudService().getByCandidateAndTags(accountCode, tags);
            if (!CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseOk(list);
            } else {
                return ResponseFactory.ResponseNoContent();
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Integer> getCountRealizedTestByAccount(String accountCode) {
        try {
            return ResponseFactory.ResponseOk(crudService().getCountRealizedTestByAccount(accountCode));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }
}
