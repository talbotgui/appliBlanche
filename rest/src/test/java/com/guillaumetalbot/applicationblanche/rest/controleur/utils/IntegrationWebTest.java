package com.guillaumetalbot.applicationblanche.rest.controleur.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;

import com.guillaumetalbot.applicationblanche.rest.application.RestApplication;

@SpringBootTest(classes = RestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationWebTest extends AbstractTestNGSpringContextTests {

	/** ContextRoot de l'application. */
	@Value("${server.context-path}")
	private String contextPath;

	/** Port sur lequel démarre le serveur. */
	@Value("${local.server.port}")
	private int port;

	/**
	 * Reset restTemplate avec les intercepteurs
	 *
	 * @see com.guillaumetalbot.applicationblanche.rest.controleur.utils.ControlerTestUtil (REST_INTERCEPTORS)
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
