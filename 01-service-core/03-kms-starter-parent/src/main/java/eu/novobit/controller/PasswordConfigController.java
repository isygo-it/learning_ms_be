package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.PasswordConfigDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.mapper.PasswordConfigMapper;
import eu.novobit.model.PasswordConfig;
import eu.novobit.service.impl.PasswordConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Password config controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/config/password")
@CtrlDef(handler = KmsExceptionHandler.class, mapper = PasswordConfigMapper.class, service = PasswordConfigService.class)
public class PasswordConfigController extends MappedCrudController<PasswordConfig, PasswordConfigDto, PasswordConfigDto, PasswordConfigService> {
}
