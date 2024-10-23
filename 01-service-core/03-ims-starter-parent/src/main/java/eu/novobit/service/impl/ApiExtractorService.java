package eu.novobit.service.impl;

import eu.novobit.api.AbstractApiExtractor;
import eu.novobit.model.ApiPermission;
import eu.novobit.repository.ApiPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Api extractor service.
 */
@Slf4j
@Service
@Transactional
public class ApiExtractorService extends AbstractApiExtractor<ApiPermission> {

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;

    @Transactional
    @Override
    public ApiPermission saveApi(ApiPermission api) {
        Optional<ApiPermission> optional = apiPermissionRepository.findByServiceNameAndObjectAndMethodAndRqTypeAndPath(api.getServiceName()
                , api.getObject()
                , api.getMethod()
                , api.getRqType()
                , api.getPath());
        if (optional.isPresent()) {
            return optional.get();
        }
        return apiPermissionRepository.save(api);
    }

    @Override
    public ApiPermission newInstance() {
        return ApiPermission.builder().serviceName(serviceName).build();
    }
}
