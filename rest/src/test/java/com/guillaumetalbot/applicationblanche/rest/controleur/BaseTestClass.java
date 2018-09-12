package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.assertj.core.util.Arrays;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.metier.service.LibelleService;
import com.guillaumetalbot.applicationblanche.rest.application.RestApplicationForTests;
import com.guillaumetalbot.applicationblanche.rest.controleur.test.JwtIntegrationWebTest;

@SpringBootTest(classes = RestApplicationForTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BaseTestClass extends JwtIntegrationWebTest {

	/** Mock de service créé par Mockito. */
	@MockBean
	protected ClientService clientService;

	/** Mock de service créé par Mockito. */
	@MockBean
	protected LibelleService libelleService;

	@Override
	protected String getListePackagesDeControleur() {
		return RestApplicationForTests.PACKAGE_CONTROLEUR;
	}

	@Override
	protected Object[] getListeServices() {
		return Arrays.array(super.securiteService, this.clientService, this.libelleService);
	}

}
