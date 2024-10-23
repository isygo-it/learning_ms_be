package eu.novobit.remote.quiz;

import eu.novobit.api.CandidateQuizControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * The interface Quiz candidate quiz service.
 */
@FeignClient(configuration = FeignConfig.class, name = "quiz-service", contextId = "candidate-quiz", path = "/api/private/candidate/quiz")
public interface QuizCandidateQuizService extends CandidateQuizControllerApi {

}
