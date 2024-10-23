package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.JwtProperties;
import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.TokenConfig;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.repository.TokenConfigRepository;
import eu.novobit.service.ITokenConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Token config service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@SrvRepo(value = TokenConfigRepository.class)
public class TokenConfigService extends AbstractCrudCodifiableService<TokenConfig, TokenConfigRepository> implements ITokenConfigService {

    private final JwtProperties jwtProperties;

    @Autowired
    private TokenConfigRepository tokenConfigRepository;

    /**
     * Instantiates a new Token config service.
     *
     * @param jwtProperties the jwt properties
     */
    public TokenConfigService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


    @Override
    public TokenConfig buildTokenConfig(String domain, IEnumAppToken.Types tokenType) {
        //Serach for token config configured for the domein by type
        Optional<TokenConfig> optional = tokenConfigRepository.findByDomainIgnoreCaseAndTokenType(domain, tokenType);
        if (optional.isPresent()) {
            return optional.get();
        }
        //Serach for token config configured for default by type
        optional = tokenConfigRepository.findByDomainIgnoreCaseAndTokenType(DomainConstants.DEFAULT_DOMAIN_NAME, tokenType);
        if (optional.isPresent()) {
            return optional.get();
        }
        //Build token config secified by system properties
        return TokenConfig.builder()
                .issuer(domain)
                .audience(domain)
                .signatureAlgorithm(jwtProperties.getSignatureAlgorithm().name())
                .secretKey(jwtProperties.getSecretKey())
                .lifeTimeInMs(jwtProperties.getLifeTimeInMs())
                .build();
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(TokenConfig.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RTKC")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
