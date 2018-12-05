package com.guillaumetalbot.applicationblanche.rest.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SocketService {

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	/**
	 * Envoi d'une notification à tous les navigateurs connectés
	 *
	 * @param sujet
	 * @param message
	 */
	public void envoyerUnMessage(final String sujet, final Object message) {
		this.messageTemplate.convertAndSend("/topic/" + sujet, message);
	}
}
