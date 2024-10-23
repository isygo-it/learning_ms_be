package eu.novobit.exception;
/**
 * @author novobit
 */

import eu.novobit.annotation.MsgLocale;

/**
 * The type Invalid field exception.
 */
@MsgLocale("invalid.field.exception")
public class InvalidFieldException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Invalid field exception.
     */
    public InvalidFieldException() {
    }

    /**
     * Instantiates a new Invalid field exception.
     *
     * @param message the message
     */
    public InvalidFieldException(String message) {
        super(message);
    }

}
