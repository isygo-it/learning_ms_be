package eu.novobit.exception.handler;


import eu.novobit.annotation.MsgLocale;
import eu.novobit.exception.ManagedException;


/**
 * The type Leave summary not found exception.
 */
@MsgLocale("leaveSummary.not.found.exception")
public class LeaveSummaryNotFoundException extends ManagedException {

    /**
     * Instantiates a new Leave summary not found exception.
     *
     * @param s the s
     */
    public LeaveSummaryNotFoundException(String s) {
    }
}
