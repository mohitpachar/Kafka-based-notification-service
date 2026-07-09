package com.digyai.notificationservice.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // ✅ Enable both topic & queue
        registry.enableSimpleBroker("/queue", "/topic");

        // Client → Server (optional for future)
        registry.setApplicationDestinationPrefixes("/app");

        // 🔐 User-specific destination prefix
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new UserHandshakeHandler()) // 🔥 THIS WAS MISSING
                .withSockJS();
    }
}
