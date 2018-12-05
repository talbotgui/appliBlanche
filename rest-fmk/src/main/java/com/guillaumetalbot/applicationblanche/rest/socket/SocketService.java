package com.guillaumetalbot.applicationblanche.rest.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SocketService {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(SocketService.class);

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	/**
	 * Envoi d'une notification à tous les navigateurs connectés
	 *
	 * @param sujet
	 * @param message
	 */
	public void envoyerUnMessage(final String sujet, final Object message) {
		LOG.info("Notification sur le topic '{}' avec le message '{}'", sujet, message);
		this.messageTemplate.convertAndSend("/topic/" + sujet, message);
	}
}
