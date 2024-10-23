package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.TopicControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.TopicDto;
import eu.novobit.exception.handler.LmsExceptionHandler;
import eu.novobit.mapper.TopicMapper;
import eu.novobit.model.Topic;
import eu.novobit.service.impl.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Topic controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = LmsExceptionHandler.class, mapper = TopicMapper.class, service = TopicService.class)
@RequestMapping(value = "/api/private/topic")
public class TopicController extends MappedCrudController<Topic, TopicDto, TopicDto, TopicService> implements TopicControllerApi {

}
