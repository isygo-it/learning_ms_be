package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.ApplicationDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.ApplicationMapper;
import eu.novobit.model.Application;
import eu.novobit.service.impl.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type App image controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/application")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = ApplicationMapper.class, service = ApplicationService.class)
public class AppImageController extends MappedImageController<Application, ApplicationDto, ApplicationDto, ApplicationService> {

}
