package ro.ubb.mp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple broker for broadcasting messages (topics)
        config.enableSimpleBroker("/topic", "/queue");  // Topic for public messages, queue for private
        config.setApplicationDestinationPrefixes("/app");  // Prefix for sending messages from client to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*") // Allow localhost access
                .withSockJS();  // Fallback for browsers that don't support native WebSocket
    }
}
