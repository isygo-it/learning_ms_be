package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.AppParameterControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.AppParameterDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.AppParameterMapper;
import eu.novobit.model.AppParameter;
import eu.novobit.service.impl.AppParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type App parameter controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = ImsExceptionHandler.class, mapper = AppParameterMapper.class, service = AppParameterService.class)
@RequestMapping(path = "/api/private/appParameter")
public class AppParameterController extends MappedCrudController<AppParameter, AppParameterDto, AppParameterDto, AppParameterService>
        implements AppParameterControllerApi {

    @Override
    public ResponseEntity<String> getValueByDomainAndName(RequestContextDto requestContext,
                                                          String domain, String name, Boolean allowDefault, String defaultValue) {
        log.info("Call api getPropertyByAccount {} /{}", domain, name);
        try {
            return ResponseFactory.ResponseOk(crudService().getValueByDomainAndName(domain, name, true, defaultValue));
        } catch (Throwable e) {
            log.error("<Error>: Call api getPropertyByAccount {} /{} {}", domain, name, e);
            return getBackExceptionResponse(e);
        }
    }
}
