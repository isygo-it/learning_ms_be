package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.QuizControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.dto.data.QuizListDto;
import eu.novobit.dto.data.QuizQuestionDto;
import eu.novobit.dto.data.QuizReportDto;
import eu.novobit.exception.handler.QuizExceptionHandler;
import eu.novobit.mapper.QuizListMapper;
import eu.novobit.mapper.QuizMapper;
import eu.novobit.mapper.QuizQuestionMapper;
import eu.novobit.model.Quiz;
import eu.novobit.service.ICandidateQuizService;
import eu.novobit.service.impl.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * The type Quiz controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = QuizExceptionHandler.class, mapper = QuizMapper.class, service = QuizService.class)
@RequestMapping(value = "/api/private/quiz")
public class QuizController extends MappedCrudController<Quiz, QuizDto, QuizDto, QuizService>
        implements QuizControllerApi {

    @Autowired
    private QuizQuestionMapper quizQuestionMapper;
    @Autowired
    private ICandidateQuizService candidateQuizService;
    @Autowired
    private QuizListMapper quizListMapper;

    @Override
    public ResponseEntity<Resource> downloadQuestionImage(RequestContextDto requestContext, Long id) throws IOException {
        log.info("Download question image request received");
        try {
            Resource imageResource = crudService().downloadQuestionImage(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imageResource.getFile().toPath()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
                    .body(imageResource);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<QuizQuestionDto> uploadQuestionImage(RequestContextDto requestContext, Long id, MultipartFile file) {
        log.info("Upload question image request received");
        try {
            return ResponseFactory.ResponseOk(quizQuestionMapper.entityToDto(crudService().uploadQuestionImage(id,
                    requestContext.getSenderDomain(), file)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<QuizListDto>> getQuizCodesByCategory(RequestContextDto requestContext, String category) {
        log.info("get Quiz Codes By Category request received");
        try {
            List<Quiz> list = crudService().getQuizCodesByCategory(category);
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(quizListMapper.listEntityToDto(list));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<QuizDto> findByCode(RequestContextDto requestContext, String code) {
        log.info("get quiz by code received");
        try {
            QuizDto quizDto = mapper().entityToDto(crudService().getQuizByCode(code));
            if (quizDto == null) {
                return ResponseFactory.ResponseNoContent();
            }

            return ResponseFactory.ResponseOk(this.afterFindById(quizDto));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public List<QuizDto> afterFindAll(RequestContextDto requestContext, List<QuizDto> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(quizDto -> {
                QuizReportDto quizReport = candidateQuizService.getReport(quizDto.getCode(), requestContext.getSenderUser());
                if (quizReport != null) {
                    quizDto.setStartDate(quizReport.getStartDate());
                    quizDto.setSubmitDate(quizReport.getSubmitDate());
                    quizDto.setScore(quizReport.getScore());
                    quizDto.setScale(quizReport.getScale());
                }
            });
        }
        return super.afterFindAll(requestContext, list);
    }
}
