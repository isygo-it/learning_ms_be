package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Employee;
import eu.novobit.model.Passport;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.PassportRepository;
import eu.novobit.service.IPassportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Passport service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = PassportRepository.class)
public class PassportService extends AbstractCrudImageService<Passport, PassportRepository> implements IPassportService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Passport service.
     *
     * @param appProperties the app properties
     */
    public PassportService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Passport.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RPSPRT")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    protected String getUploadDirectory() {
        return appProperties.getUploadDirectory();
    }

}
