package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Cin;
import eu.novobit.model.Employee;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.CinRepository;
import eu.novobit.service.ICinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Cin service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = CinRepository.class)
public class CinService extends AbstractCrudImageService<Cin, CinRepository> implements ICinService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Cin service.
     *
     * @param appProperties the app properties
     */
    public CinService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Cin.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RCIN")
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
