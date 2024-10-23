package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.PassportDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.PassportMapper;
import eu.novobit.model.Passport;
import eu.novobit.service.impl.PassportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Passport controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = PassportMapper.class, service = PassportService.class)
@RequestMapping(value = "/api/private/passport")
public class PassportController extends MappedImageController<Passport, PassportDto, PassportDto, PassportService> {

}
