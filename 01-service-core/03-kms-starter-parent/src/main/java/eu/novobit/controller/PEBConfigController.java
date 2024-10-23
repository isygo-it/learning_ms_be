package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.PEBConfigDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.mapper.PEBConfigMapper;
import eu.novobit.model.PEBConfig;
import eu.novobit.service.impl.PEBConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Peb config controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/config/peb")
@CtrlDef(handler = KmsExceptionHandler.class, mapper = PEBConfigMapper.class, service = PEBConfigService.class)
public class PEBConfigController extends MappedCrudController<PEBConfig, PEBConfigDto, PEBConfigDto, PEBConfigService> {

}
