package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractCamelProcessor;
import eu.novobit.dto.wsocket.WsMessageDto;
import eu.novobit.dto.wsocket.WsMessageWrapperDto;
import eu.novobit.dto.wsocket.WsSubscribeDto;
import eu.novobit.enumerations.IEnumWSBroker;
import eu.novobit.enumerations.IEnumWSMessage;
import eu.novobit.enumerations.IEnumWsEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Ws chat subscribe processor.
 */
@Slf4j
@Component
@Qualifier("wsChatSubscribeProcessor")
public class WsChatSubscribeProcessor extends AbstractCamelProcessor<WsSubscribeDto> {

    @Override
    public void performProcessor(Exchange exchange, WsSubscribeDto wsSubscribe) throws Exception {
        WsMessageWrapperDto message = WsMessageWrapperDto.builder()
                .endPoint(IEnumWsEndpoint.Types.CHAT)
                .broker(IEnumWSBroker.Types.GROUP)
                .senderId(wsSubscribe.getSenderId())
                .message(WsMessageDto.builder()
                        .type(IEnumWSMessage.Types.STATUS)
                        .senderId(wsSubscribe.getSenderId())
                        .content(wsSubscribe.getStatus().name())
                        .build())
                .build();

        exchange.getIn().setHeader("receiverId", wsSubscribe.getGroupId());
        exchange.getIn().setBody(message);
        exchange.getIn().setHeader(AbstractCamelProcessor.RETURN_HEADER, true);
    }
}
