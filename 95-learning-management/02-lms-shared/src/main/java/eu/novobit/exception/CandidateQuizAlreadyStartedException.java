package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Candidate quiz already started exception.
 */
@MsgLocale("candidate.quiz.already.started.exception")
public class CandidateQuizAlreadyStartedException extends ManagedException {

    /**
     * Instantiates a new Candidate quiz already started exception.
     *
     * @param s the s
     */
    public CandidateQuizAlreadyStartedException(String s) {
    }
}
