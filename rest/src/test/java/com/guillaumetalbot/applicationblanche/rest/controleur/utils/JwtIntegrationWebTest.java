package com.guillaumetalbot.applicationblanche.rest.controleur.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.guillaumetalbot.applicationblanche.rest.application.InitialisationDonneesService;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametreDeConnexionDto;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtIntegrationWebTest extends IntegrationWebTest {

	/** Jeton JWT récupéré au login avant l'exécution de tout test. */
	private String jetonJwt;
	private String jetonJwtEcrase;

	@Autowired
	private ParametresJwt parametresJwt;

	/**
	 * Ecrase la valeur du jeton JWT récupéré au login dans la methode @BeforeClass
	 */
	protected void ecraseJetonJwtBidon() {
		this.sauvegardeJetonJwt();
		this.jetonJwt = "azertyuiop";
	}

	protected void ecraseJetonJwtExpire() {
		this.sauvegardeJetonJwt();
		this.jetonJwt = Jwts.builder().setSubject(InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP)//
				.setExpiration(new Date(System.currentTimeMillis() - 1))//
				.signWith(SignatureAlgorithm.HS512, this.parametresJwt.getSecret())//
				.compact();
	}

	/**
	 * Ecrase la valeur du jeton JWT récupéré au login dans la methode @BeforeClass
	 */
	protected void ecraseJetonJwtNull() {
		this.sauvegardeJetonJwt();
		this.jetonJwt = null;
	}

	protected ParametresJwt getParametresJwt() {
		return this.parametresJwt;
	}

	@Override
	protected RestTemplate getREST() {
		final RestTemplate rest = super.getREST();

		// si un jeton est disponible, on ajoute dans RestTemplate l'intercepteur qui introduira le header dans toutes les requêtes
		if (this.jetonJwt != null) {
			final List<ClientHttpRequestInterceptor> intercepteurs = new ArrayList<>(rest.getInterceptors());
			intercepteurs.add(new HttpHeaderInterceptor(this.parametresJwt.getHeaderKey(), this.jetonJwt));
			rest.setInterceptors(intercepteurs);
		}
		return rest;
	}

	@BeforeClass
	public void login() {

		// Appel au login
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests(
				InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP, InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP);
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

	/**
	 * Restaure le jeton JWT s'il a été précédemment écrasé
	 */
	protected void restaureJetonJwt() {
		if (this.jetonJwtEcrase != null) {
			this.jetonJwt = this.jetonJwtEcrase;
		}
	}

	/**
	 * Sauvegarde le jeton JWT.
	 */
	private void sauvegardeJetonJwt() {
		if (this.jetonJwtEcrase == null) {
			this.jetonJwtEcrase = this.jetonJwt;
		}
	}
}
