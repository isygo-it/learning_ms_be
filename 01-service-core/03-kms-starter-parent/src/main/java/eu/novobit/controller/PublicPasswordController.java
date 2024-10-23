package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.PublicPasswordControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.UserContextDto;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Public password controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(KmsExceptionHandler.class)
@RequestMapping(path = "/api/public/password")
public class PublicPasswordController extends AbstractController implements PublicPasswordControllerApi {

    @Autowired
    private ITokenService tokenService;

    @Override
    public ResponseEntity<Boolean> generateForgotPasswordAccessToken(UserContextDto userContextDto) {
        log.info("Call generateForgotPasswordAccessToken " + userContextDto.toString());
        try {
            tokenService.createForgotPasswordAccessToken(userContextDto.getDomain(),
                    userContextDto.getApplication(),
                    userContextDto.getUserName());
            return ResponseFactory.ResponseOk(true);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
