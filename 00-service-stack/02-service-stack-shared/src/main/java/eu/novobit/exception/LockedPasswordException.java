package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Locked password exception.
 */
@MsgLocale("locked.password.exception")
public class LockedPasswordException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Locked password exception.
     *
     * @param message the message
     */
    public LockedPasswordException(String message) {
        super(message);
    }

}
