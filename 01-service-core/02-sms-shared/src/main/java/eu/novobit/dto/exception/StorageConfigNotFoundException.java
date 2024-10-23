package eu.novobit.dto.exception;

import eu.novobit.annotation.MsgLocale;
import eu.novobit.exception.ManagedException;


/**
 * The type Storage config not found exception.
 */
@MsgLocale("storageConfig.get.object.exception")
public class StorageConfigNotFoundException extends ManagedException {

    /**
     * Instantiates a new Storage config not found exception.
     *
     * @param message the message
     */
    public StorageConfigNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Storage config not found exception.
     *
     * @param throwable the throwable
     */
    public StorageConfigNotFoundException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new Storage config not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public StorageConfigNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
