package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.IncrementalKeyControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.NextCodeModelDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.model.AppNextCode;
import eu.novobit.service.IKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Incremental key controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(KmsExceptionHandler.class)
@RequestMapping(path = "/api/private/key")
public class IncrementalKeyController extends AbstractController implements IncrementalKeyControllerApi {

    @Autowired
    private IKeyService keyService;


    @Override
    public ResponseEntity<String> generateNextCode(RequestContextDto requestContext,
                                                   String domain, String entity, String attribute) {
        log.info("Call generate next code for: {}/{}/{}", domain, entity, attribute);
        try {
            return ResponseFactory.ResponseOk(keyService.getIncrementalKey(domain, entity, attribute));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> subscribeNextCode(//RequestContextDto requestContext,
                                                    String domain, NextCodeModelDto incrementalConfig) {
        log.info("Call subscribe next code generator for: {}/{}", domain, incrementalConfig);
        try {
            keyService.subscribeIncrementalKeyGenerator(AppNextCode.builder()
                    .domain(domain)
                    .entity(incrementalConfig.getEntity())
                    .attribute(incrementalConfig.getAttribute())
                    .prefix(incrementalConfig.getPrefix())
                    .suffix(incrementalConfig.getSuffix())
                    .valueLength(incrementalConfig.getValueLength())
                    .value(incrementalConfig.getValue())
                    .increment(incrementalConfig.getIncrement())
                    .build());

            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}