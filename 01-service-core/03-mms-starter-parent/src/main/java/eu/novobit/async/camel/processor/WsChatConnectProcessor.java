package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractCamelProcessor;
import eu.novobit.dto.wsocket.WsConnectDto;
import eu.novobit.dto.wsocket.WsMessageDto;
import eu.novobit.dto.wsocket.WsMessageWrapperDto;
import eu.novobit.enumerations.IEnumWSBroker;
import eu.novobit.enumerations.IEnumWSMessage;
import eu.novobit.enumerations.IEnumWsEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Ws chat connect processor.
 */
@Slf4j
@Component
@Qualifier("wsChatConnectProcessor")
public class WsChatConnectProcessor extends AbstractCamelProcessor<WsConnectDto> {

    @Override
    public void performProcessor(Exchange exchange, WsConnectDto wsConnect) throws Exception {
        WsMessageWrapperDto message = WsMessageWrapperDto.builder()
                .endPoint(IEnumWsEndpoint.Types.CHAT)
                .broker(IEnumWSBroker.Types.GROUP)
                .senderId(wsConnect.getSenderId())
                .message(WsMessageDto.builder()
                        .type(IEnumWSMessage.Types.STATUS)
                        .senderId(wsConnect.getSenderId())
                        .content(wsConnect.getStatus().name())
                        .build())
                .build();

        exchange.getIn().setHeader("receiverId", wsConnect.getGroupId());
        exchange.getIn().setBody(message);
        exchange.getIn().setHeader(AbstractCamelProcessor.RETURN_HEADER, true);
    }
}
