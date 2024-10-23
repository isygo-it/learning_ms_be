package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Application not allowed exception.
 */
@MsgLocale("application.not.allowed.exception")
public class ApplicationNotAllowedException extends ManagedException {

    /**
     * Instantiates a new Application not allowed exception.
     *
     * @param s the s
     */
    public ApplicationNotAllowedException(String s) {
        super(s);
    }
}
