package com.guillaumetalbot.applicationblanche.rest.securite;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.websocket.DeploymentException;

import org.assertj.core.api.Assertions;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.rest.controleur.BaseTestClass;

public class SocketSecuriteTest extends BaseTestClass {

	/** Session courante sur le socket */
	private StompSession session;

	/**
	 * Ecouter sur le websocket
	 *
	 * @param topic
	 * @return
	 */
	private BlockingQueue<String> ecouterLeWebSocket(final String topic) {

		// File dans laquelle finissent les messages
		final BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();

		// Handler de frame qui récupère les messages et les place dans le file
		final StompFrameHandler frameHandler = new StompFrameHandler() {
			@Override
			public Type getPayloadType(final StompHeaders headers) {
				return byte[].class;
			}

			@Override
			public void handleFrame(final StompHeaders headers, final Object payload) {
				blockingQueue.offer(new String((byte[]) payload));
			}
		};

		// Souscription
		this.session.subscribe(topic, frameHandler);

		// renvoi de la file pour lire les messages récupérés
		return blockingQueue;
	}

	/**
	 * Envoi du message
	 *
	 * @param topic
	 * @param message
	 */
	private void envoyerMessageAuWebSocket(final String topic, final String message) {
		this.session.send(topic, message.getBytes());
	}

	/**
	 * Initialisation d'une session du socket
	 *
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	private void initialiserSession() throws InterruptedException, ExecutionException, TimeoutException {

		// Calcul de l'URL
		final String url = super.getURL().replaceFirst("http:", "ws:") + "/websocket";

		// Handler de session
		final StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
		};

		// Création du client
		final WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());

		// Ajout de l'entete de sécurité
		final WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
		final StompHeaders connectFrameHeaders = new StompHeaders();
		connectFrameHeaders.add(super.getParametresJwt().getHeaderKey(), super.getJetonJwt());

		// connexion (1s de timeout)
		this.session = stompClient.connect(url, httpHeaders, connectFrameHeaders, sessionHandler).get(1, TimeUnit.SECONDS);
	}

	@Test
	public void test01CasNominal()
			throws DeploymentException, IOException, URISyntaxException, InterruptedException, ExecutionException, TimeoutException {

		// Arrange : Connexion au socket
		final String topic = "/topic/test";
		final String message = "bonjour";

		// Act : Envoi et réception d'un message
		this.initialiserSession();
		final BlockingQueue<String> file = this.ecouterLeWebSocket(topic);
		this.envoyerMessageAuWebSocket(topic, message);

		// Assert
		Assert.assertEquals(file.poll(5, TimeUnit.SECONDS), message);
	}

	@Test
	public void test02CasKoSansJeton()
			throws DeploymentException, IOException, URISyntaxException, InterruptedException, ExecutionException, TimeoutException {

		//

		//
		super.ecraseJetonJwtNull();
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.initialiserSession();
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), TimeoutException.class);
	}

	@Test
	public void test03CasKoAvecJetonPerime()
			throws DeploymentException, IOException, URISyntaxException, InterruptedException, ExecutionException, TimeoutException {

		//

		//
		super.ecraseJetonJwtExpire();
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.initialiserSession();
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), TimeoutException.class);
	}
}
