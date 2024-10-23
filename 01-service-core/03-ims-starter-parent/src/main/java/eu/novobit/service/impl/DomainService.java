package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Domain;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.DomainRepository;
import eu.novobit.service.IDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The type Domain service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = DomainRepository.class)
public class DomainService extends AbstractCrudImageService<Domain, DomainRepository> implements IDomainService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Domain service.
     *
     * @param appProperties the app properties
     */
    public DomainService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public List<String> getAllDomainNames(String domain) {
        if (DomainConstants.SUPER_DOMAIN_NAME.equals(domain)) {
            return repository().getAllNames(); //.findAll().stream().map(domain -> domain.getName()).toList();
        }

        return Arrays.asList(domain);
    }

    @Override
    public Domain updateAdminStatus(Long id, IEnumBinaryStatus.Types newStatus) {
        repository().updateAdminStatusById(id, newStatus);
        return repository().findById(id).orElse(null);
    }

    @Override
    public String getImage(String domainName) {
        Optional<Domain> domainOptional = repository().findByNameIgnoreCase(domainName);
        if (domainOptional.isPresent()) {
            return domainOptional.get().getImagePath();
        }
        return "";
    }

    @Override
    public Long findDomainIdbyDomainName(String name) {
        Optional<Domain> domain = repository().findByNameIgnoreCase(name);
        if (domain.isPresent()) {
            return domain.get().getId();
        }
        return null;
    }

    @Override
    public Domain findByName(String name) {
        Optional<Domain> domain = repository().findByNameIgnoreCase(name);
        if (domain.isPresent()) {
            return domain.get();
        }
        return null;
    }

    @Override
    public boolean isEnabled(String domain) {
        return repository().getAdminStatus() == IEnumBinaryStatus.Types.ENABLED;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Domain.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RDOM")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }
}
