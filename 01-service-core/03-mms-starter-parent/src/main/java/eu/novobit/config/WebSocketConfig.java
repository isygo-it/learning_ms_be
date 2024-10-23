package eu.novobit.config;

import eu.novobit.enumerations.IEnumWSBroker;
import eu.novobit.enumerations.IEnumWsEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * The type Web socket config.
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Autowired
    private WsChannelInterceptor wsChannelInterceptor;

    //Can be tested on https://jxy.me/websocket-debug-tool/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(
                        "/socket/" + IEnumWsEndpoint.Types.NOTIFICATION.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.CHAT.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.VISIO.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.LOGIN.name().toLowerCase())
                .setAllowedOriginPatterns("*");
        registry.addEndpoint(
                        "/socket/" + IEnumWsEndpoint.Types.NOTIFICATION.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.CHAT.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.VISIO.name().toLowerCase(),
                        "/socket/" + IEnumWsEndpoint.Types.LOGIN.name().toLowerCase())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(
                /* notification topics */
                "/" + IEnumWsEndpoint.Types.NOTIFICATION.name().toLowerCase() + "/" + IEnumWSBroker.Types.USER.name().toLowerCase(),
                "/" + IEnumWsEndpoint.Types.NOTIFICATION.name().toLowerCase() + "/" + IEnumWSBroker.Types.GROUP.name().toLowerCase(),
                "/" + IEnumWsEndpoint.Types.NOTIFICATION.name().toLowerCase() + "/" + IEnumWSBroker.Types.ALL.name().toLowerCase(),
                /* chat topics */
                "/" + IEnumWsEndpoint.Types.CHAT.name().toLowerCase() + "/" + IEnumWSBroker.Types.USER.name().toLowerCase(),
                "/" + IEnumWsEndpoint.Types.CHAT.name().toLowerCase() + "/" + IEnumWSBroker.Types.GROUP.name().toLowerCase(),
                /* login topics */
                "/" + IEnumWsEndpoint.Types.LOGIN.name().toLowerCase() + "/" + IEnumWSBroker.Types.USER.name().toLowerCase());
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(wsChannelInterceptor);
    }
}
