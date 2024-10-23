package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.VacationDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.VacationMapper;
import eu.novobit.model.Vacation;
import eu.novobit.service.impl.VacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Vacation controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = VacationMapper.class, service = VacationService.class)
@RequestMapping(value = "/api/private/vacation")
public class VacationController extends MappedCrudController<Vacation, VacationDto, VacationDto, VacationService> {


}
