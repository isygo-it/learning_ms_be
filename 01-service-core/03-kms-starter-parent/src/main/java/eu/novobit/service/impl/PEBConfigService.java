package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.PEBConfig;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.repository.PEBConfigRepository;
import eu.novobit.service.IPEBConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Peb config service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@SrvRepo(value = PEBConfigRepository.class)
public class PEBConfigService extends AbstractCrudCodifiableService<PEBConfig, PEBConfigRepository> implements IPEBConfigService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(PEBConfig.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RPEB")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
