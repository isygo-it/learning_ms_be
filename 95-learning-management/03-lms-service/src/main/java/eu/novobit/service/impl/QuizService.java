package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.AppProperties;
import eu.novobit.exception.EmptyPathException;
import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.exception.ResourceNotFoundException;
import eu.novobit.helper.FileHelper;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Quiz;
import eu.novobit.model.QuizQuestion;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.QuizQuestionRepository;
import eu.novobit.repository.QuizRepository;
import eu.novobit.service.IQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private final AppProperties appProperties;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    /**
     * Instantiates a new Quiz service.
     *
     * @param appProperties the app properties
     */
    public QuizService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

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

    @Override
    public Resource downloadQuestionImage(Long id) throws MalformedURLException {
        Optional<QuizQuestion> optional = quizQuestionRepository.findById(id);
        if (optional.isPresent()) {
            if (StringUtils.hasText(optional.get().getImagePath())) {
                Resource resource = new UrlResource(Path.of(optional.get().getImagePath()).toUri());
                if (!resource.exists()) {
                    throw new ResourceNotFoundException("for path " + optional.get().getImagePath());
                }
                return resource;
            } else {
                throw new EmptyPathException("for id " + id);
            }
        } else {
            throw new ResourceNotFoundException("with id " + id);
        }
    }

    @Override
    public QuizQuestion uploadQuestionImage(Long id, String domain, MultipartFile file) throws IOException {
        Optional<QuizQuestion> optional = quizQuestionRepository.findById(id);
        if (optional.isPresent()) {
            if (file != null) {
                optional.get().setImagePath(FileHelper.storeMultipartFile(this.getUploadDirectory()
                                + File.separator
                                + domain
                                + File.separator
                                + optional.get().getClass().getSimpleName().toLowerCase()
                                + File.separator
                                + File.separator
                                + "image",
                        file.getOriginalFilename() + "_" + optional.get().getId(), file, "png").toString());
            }
            return quizQuestionRepository.save(optional.get());
        } else {
            throw new ObjectNotFoundException("with id " + id);
        }
    }

    @Override
    public List<Quiz> getQuizCodesByCategory(String category) {
        return repository().findByCategoryIn(Arrays.asList(category));
    }

    @Override
    public Quiz getQuizByCode(String code) {
        return repository().findByCodeIgnoreCase(code).orElse(null);
    }

    /**
     * Gets upload directory.
     *
     * @return the upload directory
     */
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }
}
