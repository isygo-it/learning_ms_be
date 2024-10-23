package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.DigestConfigDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.mapper.DigestConfigMapper;
import eu.novobit.model.DigestConfig;
import eu.novobit.service.impl.DigestConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Digest config controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/config/digest")
@CtrlDef(handler = KmsExceptionHandler.class, mapper = DigestConfigMapper.class, service = DigestConfigService.class)
public class DigestConfigController extends MappedCrudController<DigestConfig, DigestConfigDto, DigestConfigDto, DigestConfigService> {
}
