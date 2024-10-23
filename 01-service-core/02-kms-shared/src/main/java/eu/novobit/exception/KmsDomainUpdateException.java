package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Kms domain update exception.
 */
@MsgLocale("kms.domain.update.exception")
public class KmsDomainUpdateException extends ManagedException {

    /**
     * Instantiates a new Kms domain update exception.
     *
     * @param s the s
     */
    public KmsDomainUpdateException(String s) {
        super(s);
    }
}
