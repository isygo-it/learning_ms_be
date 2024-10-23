package eu.novobit.service.impl;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumCharSet;
import eu.novobit.exception.IncrementalConfigNotFoundException;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.RandomKey;
import eu.novobit.repository.AppNextCodeRepository;
import eu.novobit.repository.RandomKeyRepository;
import eu.novobit.service.IKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Key service.
 */
@Slf4j
@Service
@Transactional
public class KeyService implements IKeyService {

    @Autowired
    private AppNextCodeRepository appNextCodeRepository;

    @Autowired
    private RandomKeyGenerator randomKeyGenerator;

    @Autowired
    private RandomKeyRepository randomKeyRepository;

    @Override
    public String getRandomKey(int length, IEnumCharSet.Types charSetType) {
        return randomKeyGenerator.nextGuid(length, charSetType);
    }

    @Override
    public String getRandomKey(int length, IEnumCharSet.Types charSetType, String pattern) {
        return randomKeyGenerator.nextGuid(length, charSetType);
    }

    @Override
    public void subscribeIncrementalKeyGenerator(AppNextCode appNextCode) {
        Optional<AppNextCode> optional = appNextCodeRepository.findByDomainIgnoreCaseAndEntityAndAttribute(appNextCode.getDomain()
                , appNextCode.getEntity(), appNextCode.getAttribute());
        if (!optional.isPresent()) {
            appNextCodeRepository.save(appNextCode);
        } else {
            log.warn("Incremental key is already defined: ", appNextCode);
        }
    }

    @Override
    public String getIncrementalKey(String domain, String entityName, String attribute) throws IncrementalConfigNotFoundException {
        AppNextCode appNextCode = null;
        Optional<AppNextCode> optional = appNextCodeRepository.findByDomainIgnoreCaseAndEntityAndAttribute(domain, entityName, attribute);
        if (optional.isPresent()) {
            appNextCode = optional.get();
        } else {
            Optional<AppNextCode> defaultOptional = appNextCodeRepository.findByDomainIgnoreCaseAndEntityAndAttribute(DomainConstants.DEFAULT_DOMAIN_NAME, entityName, attribute);
            if (defaultOptional.isPresent()) {
                appNextCode = defaultOptional.get();
                appNextCode.setId(null);
                appNextCode.setDomain(domain);
                appNextCode.setValue(0L);
                appNextCode = appNextCodeRepository.save(appNextCode);
            } else {
                throw new IncrementalConfigNotFoundException("with domain/entity/attribute " + domain + "/" + entityName + "/" + attribute);
            }
        }
        appNextCodeRepository.increment(domain, entityName, appNextCode.getIncrement());
        return optional.get().getCode();
    }

    @Override
    public RandomKey createOrUpdateKeyByName(String domain, String name, String value) {
        Optional<RandomKey> optional = randomKeyRepository.findByDomainIgnoreCaseAndName(domain, name);
        if (optional.isPresent()) {
            optional.get().setValue(value);
            return randomKeyRepository.save(optional.get());
        } else {
            return randomKeyRepository.save(RandomKey.builder()
                    .domain(domain)
                    .name(name)
                    .value(value)
                    .build());
        }
    }

    @Override
    public RandomKey getKeyByName(String domain, String name) {
        Optional<RandomKey> optional = randomKeyRepository.findByDomainIgnoreCaseAndName(domain, name);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
