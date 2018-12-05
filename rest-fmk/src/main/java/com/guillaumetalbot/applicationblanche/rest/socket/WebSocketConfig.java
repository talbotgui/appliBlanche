package com.guillaumetalbot.applicationblanche.rest.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Classe permettant l'activation des sockets dans l'application.
 *
 * URL = ws://{host}:{port}/{applicationRootContext}/{socketEndpoint}
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config) {

		// Déclaration d'un broker de message inMemory sur l'URL "/topic"
		config.enableSimpleBroker("/topic");

		// Ajout d'un prefix à tous les endpoints de message
		config.setApplicationDestinationPrefixes("/messageEndPoint");
	}

	/** Déclaration de la solution de secours pour les navigateurs ne supportant pas les web socket. */
	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setAllowedOrigins("*");
	}

}
