package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Next code service not defined exception.
 */
@MsgLocale("ncs.not.defined.exception")
public class NextCodeServiceNotDefinedException extends ManagedException {

    /**
     * Instantiates a new Next code service not defined exception.
     *
     * @param message the message
     */
    public NextCodeServiceNotDefinedException(String message) {
        super(message);
    }
}
