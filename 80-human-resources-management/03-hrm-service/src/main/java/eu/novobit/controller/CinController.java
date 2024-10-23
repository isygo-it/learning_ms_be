package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.CinDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.CinMapper;
import eu.novobit.model.Cin;
import eu.novobit.service.impl.CinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Cin controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = CinMapper.class, service = CinService.class)
@RequestMapping(value = "/api/private/cin")
public class CinController extends MappedImageController<Cin, CinDto, CinDto, CinService> {


}
