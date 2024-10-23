package eu.novobit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.dto.ResetPwdViaTokenRequestDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumPasswordStatus;
import eu.novobit.exception.TokenInvalidException;
import eu.novobit.exception.UnsuportedAuthTypeException;
import eu.novobit.exception.UserNotFoundException;
import eu.novobit.exception.UserPasswordNotFoundException;
import eu.novobit.model.Account;

/**
 * The interface Password service.
 */
public interface IPasswordService {

    /**
     * Generate random password string.
     *
     * @param domain    the domain
     * @param domainUrl the domain url
     * @param email     the email
     * @param userName  the user name
     * @param fullName  the full name
     * @param authType  the auth type
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    String generateRandomPassword(String domain, String domainUrl, String email, String userName, String fullName, IEnumAuth.Types authType) throws JsonProcessingException;


    /**
     * Force change password.
     *
     * @param domain     the domain
     * @param userName   the user name
     * @param newPasswor the new passwor
     */
    void forceChangePassword(String domain, String userName, String newPasswor);


    /**
     * Change password.
     *
     * @param domain      the domain
     * @param userName    the user name
     * @param oldPassword the old password
     * @param newPassword the new password
     */
    void changePassword(String domain, String userName, String oldPassword, String newPassword);


    /**
     * Check for pattern boolean.
     *
     * @param domain        the domain
     * @param plainPassword the plain password
     * @return the boolean
     */
    boolean checkForPattern(String domain, String plainPassword);

    /**
     * Matches enum password status . types.
     *
     * @param domain        the domain
     * @param userName      the user name
     * @param plainPassword the plain password
     * @param authType      the auth type
     * @return the enum password status . types
     * @throws UserPasswordNotFoundException the user password not found exception
     * @throws UserNotFoundException         the user not found exception
     */
    IEnumPasswordStatus.Types matches(String domain, String userName, String plainPassword, IEnumAuth.Types authType) throws UserPasswordNotFoundException, UserNotFoundException;


    /**
     * Sign password int [ ].
     *
     * @param password the password
     * @return the int [ ]
     */
    int[] signPassword(String password);

    /**
     * Is expired boolean.
     *
     * @param domain   the domain
     * @param email    the email
     * @param userName the user name
     * @param authType the auth type
     * @return the boolean
     * @throws UserPasswordNotFoundException the user password not found exception
     * @throws UserNotFoundException         the user not found exception
     */
    Boolean isExpired(String domain, String email, String userName, IEnumAuth.Types authType) throws UserPasswordNotFoundException, UserNotFoundException;

    /**
     * Reset password via token.
     *
     * @param resetPwdViaTokenRequestDto the reset pwd via token request dto
     * @throws TokenInvalidException the token invalid exception
     */
    void resetPasswordViaToken(ResetPwdViaTokenRequestDto resetPwdViaTokenRequestDto) throws TokenInvalidException;

    /**
     * Register new password string.
     *
     * @param domain      the domain
     * @param account     the account
     * @param newPassword the new password
     * @param authType    the auth type
     * @return the string
     * @throws UnsuportedAuthTypeException the unsuported auth type exception
     */
    String registerNewPassword(String domain, Account account, String newPassword, IEnumAuth.Types authType) throws UnsuportedAuthTypeException;
}
