package eu.novobit.service;

import eu.novobit.dto.request.RequestTrackingDto;
import eu.novobit.dto.response.AuthResponseDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.model.RegistredUser;

/**
 * The interface Auth service.
 */
public interface IAuthService {

    /**
     * Register new account boolean.
     *
     * @param registredNewAccount the registred new account
     * @return the boolean
     */
    boolean registerNewAccount(RegistredUser registredNewAccount);

    /**
     * Authenticate auth response dto.
     *
     * @param requestTracking the request tracking
     * @param domain          the domain
     * @param userName        the user name
     * @param application     the application
     * @param password        the password
     * @param authType        the auth type
     * @return the auth response dto
     */
    AuthResponseDto authenticate(RequestTrackingDto requestTracking, String domain, String userName, String application, String password, IEnumAuth.Types authType);
}
