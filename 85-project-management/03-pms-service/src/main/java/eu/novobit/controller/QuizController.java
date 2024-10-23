package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.QuizControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.exception.handler.PmsExceptionHandler;
import eu.novobit.mapper.QuizMapper;
import eu.novobit.model.Quiz;
import eu.novobit.service.impl.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Quiz controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = PmsExceptionHandler.class, mapper = QuizMapper.class, service = QuizService.class)
@RequestMapping(value = "/api/private/quiz")
public class QuizController extends MappedCrudController<Quiz, QuizDto, QuizDto, QuizService> implements QuizControllerApi {

}
