package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.RoleInfo;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.RoleInfoRepository;
import eu.novobit.service.IRoleInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Role info service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = RoleInfoRepository.class)
public class RoleInfoService extends AbstractCrudCodifiableService<RoleInfo, RoleInfoRepository> implements IRoleInfoService {


    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(RoleInfo.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RROL")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    public RoleInfo findByName(String name) {
        return repository().findByName(name).orElse(null);
    }
}