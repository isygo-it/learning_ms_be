package eu.novobit.exception;
/**
 * @author novobit
 */


import eu.novobit.annotation.MsgLocale;

/**
 * The type Backend service exception.
 */
@MsgLocale("backend.service.exception")
public class BackendServiceException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Backend service exception.
     */
    public BackendServiceException() {
    }

    /**
     * Instantiates a new Backend service exception.
     *
     * @param message the message
     */
    public BackendServiceException(String message) {
        super(message);
    }
}
