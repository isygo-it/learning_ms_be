package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.enumerations.IEnumCharSet;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.service.IKeyService;
import eu.novobit.service.KeyServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Key controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(KmsExceptionHandler.class)
@RequestMapping(path = "/api/private/key")
public class KeyController extends AbstractController implements KeyServiceApi {

    @Autowired
    private IKeyService keyService;

    @Override
    public ResponseEntity<String> generateRandomKey(RequestContextDto requestContextDto,
                                                    Integer length, IEnumCharSet.Types charSetType) {
        log.info("Call generateRandomKey");
        try {
            return ResponseFactory.ResponseOk(keyService.getRandomKey(length, charSetType));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> renewKeyByName(RequestContextDto requestContextDto,
                                                 String domain, String keyName, Integer length, IEnumCharSet.Types charSetType) {
        log.info("Call generateRandomKeyName");
        try {
            String keyValue = keyService.getRandomKey(length, charSetType);
            keyService.createOrUpdateKeyByName(domain, keyName, keyValue);
            return ResponseFactory.ResponseOk(keyValue);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> getKeyByName(RequestContextDto requestContextDto,
                                               String domain, String keyName) {
        log.info("Call getRandomKeyName");
        try {
            return ResponseFactory.ResponseOk(keyService.getKeyByName(domain, keyName).getValue());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}