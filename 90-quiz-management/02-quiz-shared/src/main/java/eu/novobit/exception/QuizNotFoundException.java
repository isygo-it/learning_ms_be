package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Quiz not found exception.
 */
@MsgLocale("quiz.not.found.exception")
public class QuizNotFoundException extends ManagedException {

    /**
     * Instantiates a new Quiz not found exception.
     *
     * @param s the s
     */
    public QuizNotFoundException(String s) {
    }
}
