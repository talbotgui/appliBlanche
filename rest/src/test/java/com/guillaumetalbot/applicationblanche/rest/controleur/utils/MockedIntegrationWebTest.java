package com.guillaumetalbot.applicationblanche.rest.controleur.utils;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

public class MockedIntegrationWebTest extends IntegrationWebTest {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MockedIntegrationWebTest.class);

	/** Mock de service créé par Mockito. */
	@MockBean
	protected ClientService clientService;

	/** Mock de service créé par Mockito. */
	@MockBean
	protected SecuriteService securiteService;

	/** Pour créer les mock. */
	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	/** Pour faire un reset de chaque mock. */
	@BeforeMethod
	public void beforeMethod() {
		LOG.info("*****************************************");
		Mockito.reset(this.clientService, this.securiteService);
	}

}
