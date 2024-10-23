package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.PasswordConfig;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.repository.PasswordConfigRepository;
import eu.novobit.service.IPasswordConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Password config service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@SrvRepo(value = PasswordConfigRepository.class)
public class PasswordConfigService extends AbstractCrudCodifiableService<PasswordConfig, PasswordConfigRepository> implements IPasswordConfigService {

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(PasswordConfig.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RPWC")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
