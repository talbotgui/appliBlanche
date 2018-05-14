package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.rest.application.InitialisationDonneesService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.MockedIntegrationWebTest;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametreDeConnexionDto;

public class SecuriteTest extends MockedIntegrationWebTest {

	@Test
	public void test01acces01SansToken() {

		// ARRANGE
		super.ecraseJetonJwtNull();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<Collection<Utilisateur>> typeRetour = new ParameterizedTypeReference<Collection<Utilisateur>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/utilisateurs", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Mockito.verifyNoMoreInteractions(super.securiteService, super.clientService);
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.FORBIDDEN.value());
		final String message = e.getResponseBodyAsString().replaceAll("\"timestamp\":\"[^\"]*\"", "\"timestamp\":\"\"");
		Assert.assertEquals(message,
				"{\"timestamp\":\"\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"Access Denied\",\"path\":\"/applicationBlanche/v1/utilisateurs\"}");

	}

	@Test
	public void test01acces02AvecTokenBidon() {
		// ARRANGE
		super.ecraseJetonJwtBidon();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<Collection<Utilisateur>> typeRetour = new ParameterizedTypeReference<Collection<Utilisateur>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/utilisateurs", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Mockito.verifyNoMoreInteractions(super.securiteService, super.clientService);
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.FORBIDDEN.value());
		final String message = e.getResponseBodyAsString().replaceAll("\"timestamp\":\"[^\"]*\"", "\"timestamp\":\"\"");
		Assert.assertEquals(message,
				"{\"timestamp\":\"\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"Access Denied\",\"path\":\"/applicationBlanche/v1/utilisateurs\"}");
	}

	@Test
	public void test01acces03AvecTokenExpire() {
		// ARRANGE
		super.ecraseJetonJwtExpire();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<Collection<Utilisateur>> typeRetour = new ParameterizedTypeReference<Collection<Utilisateur>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/utilisateurs", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Mockito.verifyNoMoreInteractions(super.securiteService, super.clientService);
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.FORBIDDEN.value());
		final String message = e.getResponseBodyAsString().replaceAll("\"timestamp\":\"[^\"]*\"", "\"timestamp\":\"\"");
		Assert.assertEquals(message,
				"{\"timestamp\":\"\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"Access Denied\",\"path\":\"/applicationBlanche/v1/utilisateurs\"}");
	}

	@Test
	public void test01acces04Valide() {
		final List<Utilisateur> toReturn = Arrays.asList(new Utilisateur("l1", "m1"), new Utilisateur("l2", "m2"), new Utilisateur("l3", "m3"));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.securiteService).listerUtilisateurs();
		super.restaureJetonJwt();

		// ACT
		final ParameterizedTypeReference<Collection<Utilisateur>> typeRetour = new ParameterizedTypeReference<Collection<Utilisateur>>() {
		};
		final ResponseEntity<Collection<Utilisateur>> utilisateurs = this.getREST().exchange(this.getURL() + "/v1/utilisateurs", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.securiteService).listerUtilisateurs();
		Mockito.verifyNoMoreInteractions(this.securiteService);
		Assert.assertNotNull(utilisateurs.getBody());
		Assert.assertEquals(utilisateurs.getBody().size(), toReturn.size());
		Assert.assertEquals(utilisateurs.getBody().iterator().next().getLogin(), toReturn.iterator().next().getLogin());

	}

	@Test
	public void test02connexion01SansDonnees() {

		//
		super.ecraseJetonJwtNull();
		final ParametreDeConnexionDto cred = null;

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			final HttpEntity<ParametreDeConnexionDto> requete = new HttpEntity<>(cred);
			super.getREST().exchange(this.getURL() + "/login", HttpMethod.POST, requete, void.class);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.FORBIDDEN.value());
		Assert.assertEquals(e.getResponseBodyAsString(), "");
	}

	@Test
	public void test02connexion02AvecDonneesBidon() {

		//
		super.ecraseJetonJwtNull();
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests("toto", "toto");

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			final HttpEntity<ParametreDeConnexionDto> requete = new HttpEntity<>(cred);
			super.getREST().exchange(this.getURL() + "/login", HttpMethod.POST, requete, void.class);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.FORBIDDEN.value());
		Assert.assertEquals(e.getResponseBodyAsString(), "");
	}

	@Test
	public void test02connexion03Valide() {

		//
		super.ecraseJetonJwtNull();
		final ParametreDeConnexionDto cred = ParametreDeConnexionDto.creerInstanceSansChiffreLeMotDePassePourUsageDansTests(
				InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP, InitialisationDonneesService.ADMIN_PAR_DEFAUT_LOGIN_MDP);

		//
		final HttpEntity<ParametreDeConnexionDto> requete = new HttpEntity<>(cred);
		final ResponseEntity<Void> reponse = super.getREST().exchange(this.getURL() + "/login", HttpMethod.POST, requete, void.class);

		//
		Assert.assertEquals(reponse.getStatusCodeValue(), HttpStatus.OK.value());
		final List<String> entetes = reponse.getHeaders().get(this.getParametresJwt().getHeaderKey());
		Assert.assertNotNull(entetes);
		Assert.assertEquals(entetes.size(), 1);
	}

}
