package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Random key not found exception.
 */
@MsgLocale("random.key.not.found.exception")
public class RandomKeyNotFoundException extends ManagedException {

    /**
     * Instantiates a new Random key not found exception.
     *
     * @param message the message
     */
    public RandomKeyNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Random key not found exception.
     *
     * @param throwable the throwable
     */
    public RandomKeyNotFoundException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new Random key not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RandomKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
