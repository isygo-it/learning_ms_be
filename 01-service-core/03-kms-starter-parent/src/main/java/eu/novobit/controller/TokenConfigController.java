package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.TokenConfigDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.mapper.TokenConfigMapper;
import eu.novobit.model.TokenConfig;
import eu.novobit.service.impl.TokenConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Token config controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/config/token")
@CtrlDef(handler = KmsExceptionHandler.class, mapper = TokenConfigMapper.class, service = TokenConfigService.class)
public class TokenConfigController extends MappedCrudController<TokenConfig, TokenConfigDto, TokenConfigDto, TokenConfigService> {
}
