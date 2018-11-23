package com.guillaumetalbot.applicationblanche.rest.controleur.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.ChiffrementUtil;
import com.guillaumetalbot.applicationblanche.rest.application.InitialisationDonneesService;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametreDeConnexionDto;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class JwtIntegrationWebTest extends MockedIntegrationWebTest {

	@Autowired
	protected ApplicationContext currentTestApplicationContext;

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

	protected abstract String getListePackagesDeControleur();

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
		final String loginMdp = InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP;
		final Utilisateur u = new Utilisateur(loginMdp, ChiffrementUtil.encrypt(loginMdp));
		final Role role = new Role(loginMdp);
		role.setRessourcesAutorisees(new HashSet<>(InitialisationDonneesService.listerMethodesDeControleurs(this.currentTestApplicationContext,
				"RestControler", this.getListePackagesDeControleur())));
		u.setRoles(new HashSet<Role>(Arrays.asList(role)));

		this.login(u);
	}

	protected void login(final Utilisateur u) {
		// Mock du service de recherche d'un utilisateur
		Mockito.doReturn(u).when(this.securiteService).chargerUtilisateurReadOnly(Mockito.anyString());
		Mockito.doReturn(u).when(this.securiteService).chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(Mockito.anyString());

		// Appel au login
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests(u.getLogin(),
				u.getLogin());
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
