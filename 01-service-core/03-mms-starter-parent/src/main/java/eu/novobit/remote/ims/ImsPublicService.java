package eu.novobit.remote.ims;

import eu.novobit.api.PublicControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Ims public service.
 */
@FeignClient(configuration = FeignConfig.class, name = "identity-service", contextId = "ims-public", path = "/api/public")
public interface ImsPublicService extends PublicControllerApi {

}
