package com.guillaumetalbot.applicationblanche.rest.controleur.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;

@TestExecutionListeners(MockitoTestExecutionListener.class)
public class IntegrationWebTest extends AbstractTestNGSpringContextTests {

	/** ContextRoot de l'application. */
	@Value("${server.servlet.context-path}")
	private String contextPath;

	/** Port sur lequel démarre le serveur. */
	@Value("${local.server.port}")
	private int port;

	/**
	 * Reset restTemplate avec les intercepteurs
	 *
	 * @see com.guillaumetalbot.applicationblanche.rest.controleur.test.ControlerTestUtil (REST_INTERCEPTORS)
	 *
	 * @return a new RestTemplate
	 */
	protected RestTemplate getREST() {
		final RestTemplate rest = new RestTemplate();
		rest.setInterceptors(ControlerTestUtil.getRestInterceptors());
		return rest;
	}

	/**
	 * Test URL.
	 *
	 * @return URL de l'application démarrée
	 */
	protected String getURL() {
		return "http://localhost:" + this.port + this.contextPath;
	}

}
