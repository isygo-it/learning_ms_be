package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Candidate answer exists exception.
 */
@MsgLocale("candidate.answer.exists.exception")
public class CandidateAnswerExistsException extends ManagedException {

    /**
     * Instantiates a new Candidate answer exists exception.
     *
     * @param s the s
     */
    public CandidateAnswerExistsException(String s) {
    }
}
