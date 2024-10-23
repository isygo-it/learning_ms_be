package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type User password not found exception.
 */
@MsgLocale("user.password.not.found.exception")
public class UserPasswordNotFoundException extends ManagedException {

    /**
     * Instantiates a new User password not found exception.
     *
     * @param message the message
     */
    public UserPasswordNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new User password not found exception.
     *
     * @param throwable the throwable
     */
    public UserPasswordNotFoundException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new User password not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserPasswordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
