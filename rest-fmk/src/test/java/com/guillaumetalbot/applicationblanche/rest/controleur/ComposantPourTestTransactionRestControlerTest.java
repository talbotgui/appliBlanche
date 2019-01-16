package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.application.RestApplicationForTests;
import com.guillaumetalbot.applicationblanche.rest.controleur.test.ControlerTestUtil;
import com.guillaumetalbot.applicationblanche.rest.controleur.test.IntegrationWebTest;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametreDeConnexionDto;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

/**
 * Ce test a pour but de valider la mécanique de gestion des transactions.
 *
 * Il est donc indépendant des autres et ne doit pas souffir des bouchons (d'où la gestion du login dans cette classe) !!
 */
@SpringBootTest(classes = RestApplicationForTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ComposantPourTestTransactionRestControlerTest extends IntegrationWebTest {

	private static final String LOGIN_MDP = "testTransaction";
	private static final String METHODE_DU_CONTROLEUR_LIE_AU_TEST = "ComposantPourTestTransaction.sauvegarderRole";

	@Autowired
	private DataSource ds;

	private String jetonJwt;

	@Autowired
	private ParametresJwt parametresJwt;

	@Autowired
	private SecuriteService securiteService;

	@BeforeClass
	public void login() {

		// création d'un utilisateur dédié à ce test
		final List<Ressource> ressourceDuControleurLieAuTest = Arrays.asList(new Ressource(METHODE_DU_CONTROLEUR_LIE_AU_TEST));
		this.securiteService.initialiserOuCompleterConfigurationSecurite(ressourceDuControleurLieAuTest, LOGIN_MDP, LOGIN_MDP, "admin");

		// Appel au login
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests(LOGIN_MDP, LOGIN_MDP);
		final HttpEntity<ParametreDeConnexionDto> requete = new HttpEntity<>(cred);
		final ResponseEntity<String> reponse = super.getREST().exchange(this.getURL() + "/login", HttpMethod.POST, requete, String.class);

		// Vérification du code de retourO
		Assert.assertEquals(reponse.getStatusCodeValue(), HttpStatus.OK.value());

		// Lecture du jeton à réutiliser dans les prochaines requêtes
		final List<String> entetes = reponse.getHeaders().get(this.parametresJwt.getHeaderKey());
		Assert.assertNotNull(entetes);
		Assert.assertEquals(entetes.size(), 1);

		// Sauvegarde du jeton dans une variable pour le réutiliser
		this.jetonJwt = entetes.get(0);
	}

	@Test
	public void test01SauvegardeClientMalgreExceptionDansControleur() {
		final String nomRole = "unNouveauRole";

		// ARRANGE
		final RestTemplate rest = super.getREST();
		final List<ClientHttpRequestInterceptor> intercepteurs = new ArrayList<>(rest.getInterceptors());
		intercepteurs.add(new HttpHeaderInterceptor(this.parametresJwt.getHeaderKey(), this.jetonJwt));
		rest.setInterceptors(intercepteurs);

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("nom", nomRole);
		final Throwable thrown = Assertions.catchThrowable(() -> {
			rest.postForEntity(this.getURL() + "/vTest/role", requestParam, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpServerErrorException.InternalServerError.class);
		final HttpServerErrorException e = (HttpServerErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), BusinessException.ERREUR_SHA.getHttpStatusCode());

		final JdbcTemplate jdbc = new JdbcTemplate(this.ds);
		Assert.assertEquals(jdbc.queryForObject("select count(*) from ROLE where NOM=?", new Object[] { nomRole }, Long.class), (Long) 1L);

	}
}
