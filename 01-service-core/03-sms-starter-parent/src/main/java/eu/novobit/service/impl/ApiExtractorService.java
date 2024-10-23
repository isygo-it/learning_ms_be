package eu.novobit.service.impl;

import eu.novobit.api.AbstractApiExtractor;
import eu.novobit.async.kafka.KafkaRegisterApisProducer;
import eu.novobit.mapper.ApiPermissionMapper;
import eu.novobit.model.ApiPermission;
import eu.novobit.repository.ApiPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private KafkaRegisterApisProducer kafkaRegisterApisProducer;
    @Autowired
    private ApiPermissionRepository apiPermissionRepository;
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;

    @Transactional
    @Override
    public ApiPermission saveApi(ApiPermission api) {
        Optional<ApiPermission> optional = apiPermissionRepository.findByServiceNameAndObjectAndMethodAndRqTypeAndPath(api.getServiceName()
                , api.getObject()
                , api.getMethod()
                , api.getRqType()
                , api.getPath());
        if (optional.isPresent()) {
            api = optional.get();
        } else {
            api = apiPermissionRepository.save(api);
        }

        try {
            kafkaRegisterApisProducer.sendMessage(apiPermissionMapper.entityToDto(api));
        } catch (IOException e) {
            log.error("<Error>: Register api permission via kafka topic failed with error: {} ", e);
        }

        return api;
    }

    @Override
    public ApiPermission newInstance() {
        return ApiPermission.builder().serviceName(serviceName).build();
    }
}
