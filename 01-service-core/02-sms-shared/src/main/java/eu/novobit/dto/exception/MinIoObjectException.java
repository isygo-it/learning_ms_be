package eu.novobit.dto.exception;

import eu.novobit.annotation.MsgLocale;
import eu.novobit.exception.ManagedException;


/**
 * The type Min io object exception.
 */
@MsgLocale("minio.get.object.exception")
public class MinIoObjectException extends ManagedException {

    /**
     * Instantiates a new Min io object exception.
     *
     * @param message the message
     */
    public MinIoObjectException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Min io object exception.
     *
     * @param throwable the throwable
     */
    public MinIoObjectException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new Min io object exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MinIoObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
