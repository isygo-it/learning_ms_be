package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Bad argument exception.
 */
@MsgLocale("bad.argument.exception")
public class BadArgumentException extends ManagedException {

    /**
     * Instantiates a new Bad argument exception.
     *
     * @param message the message
     */
    public BadArgumentException(String message) {
        super(message);
    }
}
