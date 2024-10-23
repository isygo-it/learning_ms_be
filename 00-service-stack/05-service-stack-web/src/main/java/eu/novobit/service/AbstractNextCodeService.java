package eu.novobit.service;

import eu.novobit.model.extendable.NextCodeModel;
import eu.novobit.repository.NextCodeRepository;
import eu.novobit.service.nextCode.INextCodeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Abstract next code service.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractNextCodeService<T extends NextCodeModel> implements INextCodeService<T> {

    /**
     * Next code repository next code repository.
     *
     * @return the next code repository
     */
    public abstract NextCodeRepository nextCodeRepository();

    @Override
    public T findByEntity(String entity) {
        Optional<T> nextCode = nextCodeRepository().findByEntity(entity);
        if (nextCode.isPresent()) {
            return nextCode.get();
        }
        return null;
    }

    @Override
    public T findByDomainAndEntityAndAttribute(String domain, String entity, String attribute) {
        Optional<T> nextCode = nextCodeRepository().findByDomainIgnoreCaseAndEntityAndAttribute(domain, entity, attribute);
        if (nextCode.isPresent()) {
            return nextCode.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void increment(String domain, String entity, Integer increment) {
        nextCodeRepository().increment(domain, entity, increment);
        nextCodeRepository().flush();
    }

    public T saveAndFlush(T appNextCode) {
        return (T) nextCodeRepository().saveAndFlush(appNextCode);
    }

    public T save(T appNextCode) {
        return (T) nextCodeRepository().save(appNextCode);
    }
}
