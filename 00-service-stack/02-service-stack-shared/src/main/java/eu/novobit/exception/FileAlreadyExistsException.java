package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type File already exists exception.
 */
@MsgLocale("file.already.exists.exception")
public class FileAlreadyExistsException extends ManagedException {

    /**
     * Instantiates a new File already exists exception.
     *
     * @param message the message
     */
    public FileAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new File already exists exception.
     *
     * @param throwable the throwable
     */
    public FileAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new File already exists exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
