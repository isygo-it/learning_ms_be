package eu.novobit.exception;
/**
 * @author novobit
 */


import eu.novobit.annotation.MsgLocale;

/**
 * The type To json conversion exception.
 */
@MsgLocale("to.json.conversion.exception")
public class ToJsonConversionException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new To json conversion exception.
     */
    public ToJsonConversionException() {
    }

    /**
     * Instantiates a new To json conversion exception.
     *
     * @param message the message
     */
    public ToJsonConversionException(String message) {
        super(message);
    }
}
