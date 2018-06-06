package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.rest.application.InitialisationDonneesService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.ControlerTestUtil;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.IntegrationWebTest;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametreDeConnexionDto;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

/**
 * Ce test a pour but de valider la mécanique de gestion des transactions. Il est donc indépendant des autres et ne doit pas souffir des bouchons !!T
 */
public class ComposantPourTestTransactionRestControlerTest extends IntegrationWebTest {

	@Autowired
	private DataSource ds;

	private String jetonJwt;

	@Autowired
	private ParametresJwt parametresJwt;

	@BeforeClass
	public void login() {

		// Appel au login
		final String loginMdp = InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP;
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests(loginMdp, loginMdp);
		final HttpEntity<ParametreDeConnexionDto> requete = new HttpEntity<>(cred);
		final ResponseEntity<Void> reponse = super.getREST().exchange(this.getURL() + "/login", HttpMethod.POST, requete, void.class);

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
		final String nomClient = "unNouveauClient";

		// ARRANGE
		final RestTemplate rest = super.getREST();
		final List<ClientHttpRequestInterceptor> intercepteurs = new ArrayList<>(rest.getInterceptors());
		intercepteurs.add(new HttpHeaderInterceptor(this.parametresJwt.getHeaderKey(), this.jetonJwt));
		rest.setInterceptors(intercepteurs);

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("nom", nomClient);
		final Throwable thrown = Assertions.catchThrowable(() -> {
			rest.postForEntity(this.getURL() + "/vTest/clients", requestParam, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpServerErrorException.class);
		final HttpServerErrorException e = (HttpServerErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), BusinessException.ERREUR_SHA.getHttpStatusCode());

		final JdbcTemplate jdbc = new JdbcTemplate(this.ds);
		Assert.assertEquals(jdbc.queryForObject("select count(*) from CLIENT where NOM=?", new Object[] { nomClient }, Long.class), (Long) 1L);

	}
}
