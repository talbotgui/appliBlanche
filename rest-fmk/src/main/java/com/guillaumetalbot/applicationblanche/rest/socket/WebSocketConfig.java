package com.guillaumetalbot.applicationblanche.rest.socket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

import io.jsonwebtoken.Jwts;

/**
 * Classe permettant l'activation des sockets dans l'application.
 *
 * URL = ws://{host}:{port}/{applicationRootContext}/{socketEndpoint}
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/** Contenu du fichier application.properties lié à JWT. */
	@Autowired
	private ParametresJwt parametresJwt;

	/** Sécurisation des flux websocket */
	@Override
	public void configureClientInboundChannel(final ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {

			@Override
			public Message<?> preSend(final Message<?> message, final MessageChannel channel) {

				// récupération du context
				final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

				// Si c'est un frame/échange de connexion
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {

					// Si on a un token (IF) et qu'il est valide (exception durant le parse)
					final Object header = accessor.getNativeHeader(WebSocketConfig.this.parametresJwt.getHeaderKey());
					if (header != null && List.class.isInstance(header) && ((List<?>) header).size() == 1) {
						final String token = ((List<?>) header).get(0).toString().replace(WebSocketConfig.this.parametresJwt.getTokenPrefix(), "");

						// Parse du token
						Jwts.parser().setSigningKey(WebSocketConfig.this.parametresJwt.getSecret()).parseClaimsJws(token);

						// Sauvegarde, en contexte récupérable, de l'utilisateur
						// final Jws<Claims> jws = Jwts.parser().setSigningKey(WebSocketConfig.this.parametresJwt.getSecret()).parseClaimsJws(token);
						// final Authentication authentication = new AuthenticationToken(jws.getBody().getSubject(), null, null);
						// accessor.setUser(authentication);

						return message;
					}

					// Pas de message si l'utilisateur n'est pas autorisé
					return null;
				}

				// Fonctionnement nominal (seul le connect nécessite le token)
				return message;
			}
		});
	}

	/** Configuration des endpoints entrants et sortant. */
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
