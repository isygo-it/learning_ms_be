package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Storage exception.
 */
@MsgLocale("storage.exception")
public class StorageException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Storage exception.
     *
     * @param message the message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Storage exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
