package eu.novobit.remote.ims;

import eu.novobit.api.AppParameterControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Ims app parameter service.
 */
@FeignClient(configuration = FeignConfig.class, name = "identity-service", contextId = "ims-app-parameter", path = "/api/private/appParameter")
public interface ImsAppParameterService extends AppParameterControllerApi {

}
