package eu.novobit.service.impl;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.DigestConfig;
import eu.novobit.model.PEBConfig;
import eu.novobit.repository.DigesterConfigRepository;
import eu.novobit.repository.PEBConfigRepository;
import eu.novobit.service.ICryptoService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.digest.PooledStringDigester;
import org.jasypt.digest.StringDigester;
import org.jasypt.digest.config.SimpleStringDigesterConfig;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * The type Crypto service.
 */
@Slf4j
@Service
@Transactional
public class CryptoService implements ICryptoService {

    @Autowired
    private PEBConfigRepository pebConfigRepository;

    @Autowired
    private DigesterConfigRepository digesterConfigRepository;

    private StringEncryptor pebStringEncryptor(PEBConfig pebConfig) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(pebConfig.getPassword());
        config.setAlgorithm(pebConfig.getAlgorithm().name());
        config.setKeyObtentionIterations(pebConfig.getKeyObtentionIterations());
        config.setPoolSize(pebConfig.getPoolSize());
        config.setProviderName(pebConfig.getProviderName());
        config.setProviderClassName(pebConfig.getProviderClassName());
        config.setSaltGeneratorClassName("org.jasypt.iv." + pebConfig.getSaltGenerator().name());
        config.setStringOutputType(pebConfig.getStringOutputType().name());
        encryptor.setConfig(config);
        return encryptor;
    }

    private StringDigester digestStringEncryptor(DigestConfig digestConfig) {
        PooledStringDigester encryptor = new PooledStringDigester();
        SimpleStringDigesterConfig config = new SimpleStringDigesterConfig();
        config.setAlgorithm(digestConfig.getAlgorithm().name().replace("_", "-"));
        config.setIterations(digestConfig.getIterations());
        config.setSaltSizeBytes(digestConfig.getSaltSizeBytes());
        config.setSaltGeneratorClassName("org.jasypt.iv." + digestConfig.getSaltGenerator().name());
        config.setProviderName(digestConfig.getProviderName());
        config.setProviderClassName(digestConfig.getProviderClassName());
        config.setInvertPositionOfSaltInMessageBeforeDigesting(digestConfig.getInvertPositionOfSaltInMessageBeforeDigesting());
        config.setInvertPositionOfPlainSaltInEncryptionResults(digestConfig.getInvertPositionOfPlainSaltInEncryptionResults());
        config.setUseLenientSaltSizeCheck(digestConfig.getUseLenientSaltSizeCheck());
        config.setPoolSize(digestConfig.getPoolSize());
        config.setStringOutputType(digestConfig.getStringOutputType().name());
        encryptor.setConfig(config);
        return encryptor;
    }

    private PasswordEncryptor passwordEncryptor(DigestConfig digestConfig) {
        ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();
        SimpleStringDigesterConfig config = new SimpleStringDigesterConfig();
        config.setAlgorithm(digestConfig.getAlgorithm().name().replace("_", "-"));
        config.setIterations(digestConfig.getIterations());
        config.setSaltSizeBytes(digestConfig.getSaltSizeBytes());
        config.setSaltGeneratorClassName("org.jasypt.iv." + digestConfig.getSaltGenerator().name());
        config.setProviderName(digestConfig.getProviderName());
        config.setProviderClassName(digestConfig.getProviderClassName());
        config.setInvertPositionOfSaltInMessageBeforeDigesting(digestConfig.getInvertPositionOfSaltInMessageBeforeDigesting());
        config.setInvertPositionOfPlainSaltInEncryptionResults(digestConfig.getInvertPositionOfPlainSaltInEncryptionResults());
        config.setUseLenientSaltSizeCheck(digestConfig.getUseLenientSaltSizeCheck());
        config.setPoolSize(digestConfig.getPoolSize());
        config.setStringOutputType(digestConfig.getStringOutputType().name());
        encryptor.setConfig(config);
        return encryptor;
    }

    @Override
    public StringEncryptor getPebEncryptor(String domain) {
        List<PEBConfig> list = pebConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("peb config not found with domain " + domain);
            return stringEncryptorDefault();
        } else {
            return this.pebStringEncryptor(list.get(0));
        }
    }

    @Override
    public StringDigester getDigestEncryptor(String domain) {
        List<DigestConfig> list = digesterConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("digest config not found with domain " + domain);
            return stringDigesterDefault();
        } else {
            return this.digestStringEncryptor(list.get(0));
        }
    }

    @Override
    public PasswordEncryptor getPasswordEncryptor(String domain) {
        List<DigestConfig> list = digesterConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("password config not found with domain " + domain);
            return passwordEncryptorDefault();
        } else {
            return passwordEncryptor(list.get(0));
        }
    }

    private StringEncryptor stringEncryptorDefault() {
        List<PEBConfig> list = pebConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("No default peb config found");
            return new PooledPBEStringEncryptor();
        }
        return this.getPebEncryptor(DomainConstants.DEFAULT_DOMAIN_NAME);
    }

    private StringDigester stringDigesterDefault() {
        List<DigestConfig> list = digesterConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("No default digest config found");
            return new PooledStringDigester();
        }
        return this.getDigestEncryptor(DomainConstants.DEFAULT_DOMAIN_NAME);
    }

    private PasswordEncryptor passwordEncryptorDefault() {
        List<DigestConfig> list = digesterConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME));
        if (CollectionUtils.isEmpty(list)) {
            log.warn("No default digest config found");
            return new StrongPasswordEncryptor();
        }
        return this.getPasswordEncryptor(DomainConstants.DEFAULT_DOMAIN_NAME);
    }
}
