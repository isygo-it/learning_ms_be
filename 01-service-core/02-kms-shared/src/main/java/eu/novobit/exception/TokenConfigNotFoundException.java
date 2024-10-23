package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Token config not found exception.
 */
@MsgLocale("token.config.not.found.exception")
public class TokenConfigNotFoundException extends ManagedException {

    /**
     * Instantiates a new Token config not found exception.
     *
     * @param s the s
     */
    public TokenConfigNotFoundException(String s) {
        super(s);
    }
}
