package com.guillaumetalbot.applicationblanche.rest.controleur.utils;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.controleur.ClientRestControler;
import com.guillaumetalbot.applicationblanche.rest.controleur.RoleEtRessourceRestControler;
import com.guillaumetalbot.applicationblanche.rest.controleur.UtilisateurRestControler;

public class MockedIntegrationWebTest extends IntegrationWebTest {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MockedIntegrationWebTest.class);

	/** Instance de controleurs pour y injecter les mocks de service. */
	@Autowired
	@InjectMocks
	private ClientRestControler clientCtrl;

	/** Mock de service créé par Mockito. */
	@Mock
	protected ClientService clientService;

	/** Instance de controleurs pour y injecter les mocks de service. */
	@Autowired
	@InjectMocks
	private RoleEtRessourceRestControler roleEtRessourceCtrl;

	/** Mock de service créé par Mockito. */
	@Mock
	protected SecuriteService securiteService;

	/** Instance de controleurs pour y injecter les mocks de service. */
	@Autowired
	@InjectMocks
	private UtilisateurRestControler utilisateurCtrl;

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
