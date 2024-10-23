package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Author;
import eu.novobit.model.Topic;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.AuthorRepository;
import eu.novobit.repository.TopicRepository;
import eu.novobit.service.IAuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Author service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = AuthorRepository.class)
public class AuthorService extends AbstractCrudImageService<Author, AuthorRepository> implements IAuthorService {
    private final AppProperties appProperties;

    /**
     * Instantiates a new Resume service.
     *
     * @param appProperties the app properties
     */
    public AuthorService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Author.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("AUTH")
                .valueLength(5L)
                .value(1L)
                .increment(1)
                .build();
    }


}
