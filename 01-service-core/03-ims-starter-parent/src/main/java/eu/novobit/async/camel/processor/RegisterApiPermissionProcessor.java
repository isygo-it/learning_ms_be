package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractCamelProcessor;
import eu.novobit.com.camel.processor.AbstractStringProcessor;
import eu.novobit.dto.ApiPermissionDto;
import eu.novobit.helper.JsonHelper;
import eu.novobit.mapper.ApiPermissionMapper;
import eu.novobit.model.ApiPermission;
import eu.novobit.repository.ApiPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * The type Register api permission processor.
 */
@Slf4j
@Component
@Qualifier("registerApiPermissionProcessor")
public class RegisterApiPermissionProcessor extends AbstractStringProcessor {

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;

    @Override
    public void performProcessor(Exchange exchange, String apiPermissionMsg) throws Exception {
        ApiPermissionDto apiPermission = JsonHelper.fromJson(apiPermissionMsg, ApiPermissionDto.class);
        apiPermission.setId(null);
        exchange.getIn().setHeader("code", apiPermission.getMethod());

        //Verify if the api is already registered
        Optional<ApiPermission> optional = apiPermissionRepository.findByServiceNameAndObjectAndMethodAndRqTypeAndPath(apiPermission.getServiceName()
                , apiPermission.getObject()
                , apiPermission.getMethod()
                , apiPermission.getRqType()
                , apiPermission.getPath());
        if (optional.isPresent()) {
            // Update the existing one
            apiPermission.setId(optional.get().getId());
            apiPermissionRepository.save(apiPermissionMapper.dtoToEntity(apiPermission));
        }

        //Create a new One
        apiPermissionRepository.save(apiPermissionMapper.dtoToEntity(apiPermission));

        exchange.getIn().setHeader(AbstractCamelProcessor.RETURN_HEADER, true);
    }
}
