package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Employee;
import eu.novobit.model.InsuranceSecurity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.InsuranceSecurityRepository;
import eu.novobit.service.IInsuranceSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type Insurance insurance security service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = InsuranceSecurityRepository.class)
public class InsuranceSecurityService extends AbstractCrudImageService<InsuranceSecurity, InsuranceSecurityRepository> implements IInsuranceSecurityService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Insurance insurance security service.
     *
     * @param appProperties the app properties
     */
    public InsuranceSecurityService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(InsuranceSecurity.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RINS")
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
