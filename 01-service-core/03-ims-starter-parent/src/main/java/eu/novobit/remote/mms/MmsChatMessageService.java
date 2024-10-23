package eu.novobit.remote.mms;

import eu.novobit.api.ChatMessageControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Mms chat message service.
 */
@FeignClient(configuration = FeignConfig.class, name = "messaging-service", contextId = "mms-chat", path = "/api/private/chat")
public interface MmsChatMessageService extends ChatMessageControllerApi {
}
