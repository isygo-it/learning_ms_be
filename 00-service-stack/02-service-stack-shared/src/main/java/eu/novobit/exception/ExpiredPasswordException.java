package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Expired password exception.
 */
@MsgLocale("expired.password.exception")
public class ExpiredPasswordException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Expired password exception.
     *
     * @param message the message
     */
    public ExpiredPasswordException(String message) {
        super(message);
    }

}
