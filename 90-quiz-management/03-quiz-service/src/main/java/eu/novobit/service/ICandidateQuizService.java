package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.data.QuizDto;
import eu.novobit.dto.data.QuizReportDto;
import eu.novobit.model.CandidateQuiz;
import eu.novobit.model.CandidateQuizAnswer;

import java.util.List;

/**
 * The interface Candidate quiz service.
 */
public interface ICandidateQuizService extends ICrudServiceMethods<CandidateQuiz> {

    /**
     * Find by quiz code and account code candidate quiz.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the candidate quiz
     */
    public CandidateQuiz findByQuizCodeAndAccountCode(String quizCode, String accountCode);

    /**
     * Start candidate answer boolean.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answer      the answer
     * @return the boolean
     */
    boolean startCandidateAnswer(String quizCode, String accountCode, CandidateQuizAnswer answer);

    /**
     * Submit candidate answer boolean.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answer      the answer
     * @return the boolean
     */
    boolean submitCandidateAnswer(String quizCode, String accountCode, CandidateQuizAnswer answer);

    /**
     * Submit candidate answers boolean.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @param answers     the answers
     * @return the boolean
     */
    boolean submitCandidateAnswers(String quizCode, String accountCode, List<CandidateQuizAnswer> answers);

    /**
     * Start candidate quiz long.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the long
     */
    Long startCandidateQuiz(String quizCode, String accountCode);

    /**
     * Submit candidate quiz boolean.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the boolean
     */
    boolean submitCandidateQuiz(String quizCode, String accountCode);

    /**
     * Gets complete answer.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the complete answer
     */
    QuizDto getCompleteAnswer(String quizCode, String accountCode);

    /**
     * Gets complete answer clean.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the complete answer clean
     */
    QuizDto getCompleteAnswerClean(String quizCode, String accountCode);

    /**
     * Gets report.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the report
     */
    QuizReportDto getReport(String quizCode, String accountCode);

    /**
     * Gets candidate quiz.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the candidate quiz
     */
    QuizDto getCandidateQuiz(String quizCode, String accountCode);

    /**
     * Gets by candidate and tags.
     *
     * @param accountCode the account code
     * @param tags        the tags
     * @return the by candidate and tags
     */
    List<QuizReportDto> getByCandidateAndTags(String accountCode, List<String> tags);

    Integer getCountRealizedTestByAccount(String accountCode);
}