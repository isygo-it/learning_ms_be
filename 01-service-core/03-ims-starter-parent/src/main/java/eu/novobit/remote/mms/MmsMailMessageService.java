package eu.novobit.remote.mms;

import eu.novobit.api.MailMessageControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Mms mail message service.
 */
@FeignClient(configuration = FeignConfig.class, name = "messaging-service", contextId = "mms-email", path = "/api/private/mail")
public interface MmsMailMessageService extends MailMessageControllerApi {
}
