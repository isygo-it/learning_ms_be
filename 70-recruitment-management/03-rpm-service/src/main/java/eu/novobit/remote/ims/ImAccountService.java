package eu.novobit.remote.ims;

import eu.novobit.api.StatisticControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Ims account service.
 */
@FeignClient(configuration = FeignConfig.class, name = "identity-service", contextId = "ims-app-account", path = "/api/private/account")
public interface ImAccountService extends StatisticControllerApi {

}
