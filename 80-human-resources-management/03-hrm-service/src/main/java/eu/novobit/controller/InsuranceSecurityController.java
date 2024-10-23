package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.InsuranceSecurityDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.InsuranceSecurityMapper;
import eu.novobit.model.InsuranceSecurity;
import eu.novobit.service.impl.InsuranceSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Insurance security controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = InsuranceSecurityMapper.class, service = InsuranceSecurityService.class)
@RequestMapping(value = "/api/private/security")
public class InsuranceSecurityController extends MappedImageController<InsuranceSecurity, InsuranceSecurityDto, InsuranceSecurityDto, InsuranceSecurityService> {


}
