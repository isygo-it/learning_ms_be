package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Deprecated password exception.
 */
@MsgLocale("deprecated.password.exception")
public class DeprecatedPasswordException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Deprecated password exception.
     *
     * @param message the message
     */
    public DeprecatedPasswordException(String message) {
        super(message);
    }

}
