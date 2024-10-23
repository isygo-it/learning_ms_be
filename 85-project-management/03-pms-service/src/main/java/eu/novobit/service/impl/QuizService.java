package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Quiz;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.QuizRepository;
import eu.novobit.service.IQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Quiz service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = QuizRepository.class)
public class QuizService extends AbstractCrudCodifiableService<Quiz, QuizRepository> implements IQuizService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Quiz.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RQIZ")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
