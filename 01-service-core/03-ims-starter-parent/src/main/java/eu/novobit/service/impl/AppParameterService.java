package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.constants.DomainConstants;
import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.model.AppParameter;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.repository.AppParameterRepository;
import eu.novobit.service.IAppParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * The type App parameter service.
 */
@Slf4j
@Service
@Transactional
@SrvRepo(value = AppParameterRepository.class)
public class AppParameterService extends AbstractCrudService<AppParameter, AppParameterRepository> implements IAppParameterService {

    @CachePut(cacheNames = SchemaTableConstantName.T_APP_PARAMETER, key = "{#appParameter.domain, #appParameter.name}")
    @Override
    public AppParameter create(AppParameter appParameter) {
        return super.create(appParameter);
    }

    @CachePut(cacheNames = SchemaTableConstantName.T_APP_PARAMETER, key = "{#appParameter.domain, #appParameter.name}")
    @Override
    public AppParameter update(AppParameter appParameter) {
        return super.update(appParameter);
    }

    @Cacheable(cacheNames = SchemaTableConstantName.T_APP_PARAMETER)
    @Override
    public List<AppParameter> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    public AppParameter findById(Long id) throws ObjectNotFoundException {
        return super.findById(id);
    }

    @Cacheable(cacheNames = SchemaTableConstantName.T_APP_PARAMETER, key = "{#domain, #name}")
    @Override
    public String getValueByDomainAndName(String domain, String name, boolean allowDefault, String defaultValue) {
        Optional<AppParameter> optional = repository().findByDomainIgnoreCaseAndNameIgnoreCase(domain, name);
        if (optional.isPresent() && StringUtils.hasText(optional.get().getValue())) {
            return optional.get().getValue();
        } else if (allowDefault) {
            optional = repository().findByDomainIgnoreCaseAndNameIgnoreCase(DomainConstants.DEFAULT_DOMAIN_NAME, name);
            if (optional.isPresent() && StringUtils.hasText(optional.get().getValue())) {
                return optional.get().getValue();
            } else {
                this.create(AppParameter.builder()
                        .domain(DomainConstants.DEFAULT_DOMAIN_NAME)
                        .name(name)
                        .description(name)
                        .value(defaultValue)
                        .build());
            }
        } else {
            this.create(AppParameter.builder()
                    .domain(domain)
                    .name(name)
                    .description(name)
                    .value(defaultValue)
                    .build());
        }

        return defaultValue;
    }
}
