package com.guillaumetalbot.applicationblanche.rest.securite;

import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

//@SpringBootTest(classes = RestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class UserDetailsServiceWrapperTest extends AbstractTestNGSpringContextTests {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceWrapperTest.class);

	@Mock
	private SecuriteService securiteService;

	// @Autowired
	@InjectMocks
	private final UserDetailsServiceWrapper userDetailsServiceWrapper = new UserDetailsServiceWrapper();

	/** Pour créer les mock. */
	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	/** Pour faire un reset de chaque mock. */
	@BeforeMethod
	public void beforeMethod() {
		LOG.info("*****************************************");
		Mockito.reset(this.securiteService);
	}

	@Test
	public void test01LoadUserByUsername01nominal() {
		//
		final String login = "login";
		final Utilisateur utilisateur = new Utilisateur(login);
		Mockito.doReturn(utilisateur).when(this.securiteService).chargerUtilisateurReadOnly(login);

		//
		final UserDetails details = this.userDetailsServiceWrapper.loadUserByUsername(login);

		//
		Assert.assertEquals(utilisateur.getLogin(), details.getUsername());
		Assert.assertNotEquals(utilisateur.isVerrouille(), details.isAccountNonLocked());
		Mockito.verify(this.securiteService).chargerUtilisateurReadOnly(login);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test01LoadUserByUsername02aucunUtilisateur() {
		//
		final String login = "login";
		Mockito.doReturn(null).when(this.securiteService).chargerUtilisateurReadOnly(login);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.userDetailsServiceWrapper.loadUserByUsername(login);
		});

		//
		Assert.assertNotNull(thrown);
		Mockito.verify(this.securiteService).chargerUtilisateurReadOnly(login);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test01LoadUserByUsername03utilisateurVerouille() {
		//
		final String login = "login";
		final Utilisateur utilisateur = new Utilisateur(login);
		utilisateur.declarerConnexionEnEchec();
		utilisateur.declarerConnexionEnEchec();
		utilisateur.declarerConnexionEnEchec();
		Mockito.doReturn(utilisateur).when(this.securiteService).chargerUtilisateurReadOnly(login);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.userDetailsServiceWrapper.loadUserByUsername(login);
		});

		//
		Assert.assertNotNull(thrown);
		Mockito.verify(this.securiteService).chargerUtilisateurReadOnly(login);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test02NotifierConnexion() {
		//
		final String login = "monLogin";

		//
		this.userDetailsServiceWrapper.notifierConnexion(login, true);

		//
		Mockito.verify(this.securiteService).notifierConnexion(login, true);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}
}
