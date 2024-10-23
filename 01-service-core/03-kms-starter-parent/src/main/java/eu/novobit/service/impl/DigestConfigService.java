package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.DigestConfig;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.repository.DigesterConfigRepository;
import eu.novobit.service.IDigestConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Digest config service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@SrvRepo(value = DigesterConfigRepository.class)
public class DigestConfigService extends AbstractCrudCodifiableService<DigestConfig, DigesterConfigRepository> implements IDigestConfigService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(DigestConfig.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RDIG")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
