package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Object not found exception.
 */
@MsgLocale("object.not.found.exception")
public class ObjectNotFoundException extends ManagedException {

    /**
     * Instantiates a new Object not found exception.
     *
     * @param message the message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Object not found exception.
     *
     * @param throwable the throwable
     */
    public ObjectNotFoundException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new Object not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
