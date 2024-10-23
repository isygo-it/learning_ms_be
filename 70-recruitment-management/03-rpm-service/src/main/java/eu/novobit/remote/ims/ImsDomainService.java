package eu.novobit.remote.ims;

import eu.novobit.api.DomainControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Ims domain service.
 */
@FeignClient(configuration = FeignConfig.class, name = "identity-service", contextId = "ims-domain", path = "/api/private/domain")
public interface ImsDomainService extends DomainControllerApi {

}
