package eu.novobit.service;

import eu.novobit.model.ICodifiable;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.extendable.NextCodeModel;
import eu.novobit.service.nextCode.INextCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The type Abstract service.
 *
 * @param <T> the type parameter
 */
@Slf4j
public abstract class AbstractService<T extends NextCodeModel> implements IService {

    @Autowired
    private INextCodeService<T> nextCodeService;


    @Override
    public T initCodeGenerator() {
        return null;
    }

    @Override
    @Transactional
    public String getNextCode() {
        NextCodeModel defaultNextCode = this.createCodeGenerator();
        if (defaultNextCode != null && StringUtils.hasText(defaultNextCode.getEntity())) {
            String newCode = defaultNextCode.nextCode().getCode();
            nextCodeService.saveAndFlush((T) defaultNextCode);
            return newCode;
        } else {
            log.error("<Error>: Class code generator could not be initialized");
        }
        return null;
    }

    private final T createCodeGenerator() {
        T defaultNextCode = this.initCodeGenerator();
        if (defaultNextCode != null && StringUtils.hasText(defaultNextCode.getEntity())) {
            T nextCodeModel = nextCodeService.findByDomainAndEntityAndAttribute(defaultNextCode.getDomain()
                    , defaultNextCode.getEntity(), defaultNextCode.getAttribute());
            if (nextCodeModel == null) {
                return nextCodeService.save(initCodeGenerator());
            } else {
                return nextCodeModel;
            }
        }
        return null;
    }

    @Override
    public <E extends IIdEntity> E beforePersist(E entity) {
        if (ICodifiable.class.isAssignableFrom(entity.getClass()) && !StringUtils.hasText(((ICodifiable) entity).getCode())) {
            ((ICodifiable) entity).setCode(this.getNextCode());
        }
        return entity;
    }
}
