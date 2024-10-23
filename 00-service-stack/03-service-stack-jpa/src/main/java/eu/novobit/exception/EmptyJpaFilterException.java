package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;

/**
 * The type Empty jpa filter exception.
 */
@MsgLocale("empty.jpa.filter.exception")
public class EmptyJpaFilterException extends ManagedException {

    /**
     * Instantiates a new Empty jpa filter exception.
     *
     * @param message the message
     */
    public EmptyJpaFilterException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Empty jpa filter exception.
     *
     * @param cause the cause
     */
    public EmptyJpaFilterException(Throwable cause) {
        super(cause);
    }
}
