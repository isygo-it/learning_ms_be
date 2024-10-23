package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Application;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.ApplicationRepository;
import eu.novobit.service.IApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Application service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = ApplicationRepository.class)
public class ApplicationService extends AbstractCrudImageService<Application, ApplicationRepository>
        implements IApplicationService {

    private final AppProperties appProperties;

    /**
     * Instantiates a new Application service.
     *
     * @param appProperties the app properties
     */
    public ApplicationService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Application.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RAPPLI")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public Application updateStatus(Long id, IEnumBinaryStatus.Types newStatus) {
        (repository()).updateAdminStatusById(id, newStatus);
        return repository().findById(id).orElse(null);
    }
}
