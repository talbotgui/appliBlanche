package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import com.guillaumetalbot.applicationblanche.rest.application.RestApplicationForTests;
import com.guillaumetalbot.applicationblanche.rest.controleur.test.JwtIntegrationWebTest;

@SpringBootTest(classes = RestApplicationForTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BaseTestClass extends JwtIntegrationWebTest {

	@Override
	protected String getListePackagesDeControleur() {
		return RestApplicationForTests.PACKAGE_CONTROLEUR;
	}
}
