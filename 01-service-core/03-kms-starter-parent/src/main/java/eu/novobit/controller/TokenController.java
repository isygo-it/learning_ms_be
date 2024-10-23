package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.TokenDto;
import eu.novobit.dto.data.TokenRequestDto;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.service.ITokenService;
import eu.novobit.service.TokenServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Token controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(KmsExceptionHandler.class)
@RequestMapping(path = "/api/private/token")
public class TokenController extends AbstractController implements TokenServiceApi {

    @Autowired
    private ITokenService tokenService;

    @Override
    public ResponseEntity<TokenDto> createTokenByDomain(//RequestContextDto requestContextDto,
                                                        String domain,
                                                        String application,
                                                        IEnumAppToken.Types tokenType,
                                                        TokenRequestDto tokenRequestDto) {
        log.info("Call create Token By Domain");
        try {
            return ResponseFactory.ResponseOk(tokenService.buildTokenAndSave(domain, application, tokenType, tokenRequestDto.getSubject(), tokenRequestDto.getClaims()));
        } catch (Throwable e) {
            log.error("<Error>: create Token By Domain: {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> isTokenValid(RequestContextDto requestContextDto,
                                                String domain,
                                                String application,
                                                IEnumAppToken.Types tokenType,
                                                String token,
                                                String subject) {
        log.info("Call is Token Valid");
        try {
            return ResponseFactory.ResponseOk(tokenService.isTokenValid(domain, application, tokenType, token, subject));
        } catch (Throwable e) {
            log.error("<Error>: Invalid token: {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}