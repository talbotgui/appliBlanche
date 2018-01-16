package com.guillaumetalbot.applicationblanche.web.controleur;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.web.controleur.utils.ControlerTestUtil;
import com.guillaumetalbot.applicationblanche.web.controleur.utils.MockedIntegrationWebTest;

public class UtilisateurRestControlerTest extends MockedIntegrationWebTest {

	@Test
	public void test01GetListeUtilisateur01() {
		final List<Utilisateur> toReturn = Arrays.asList(new Utilisateur("l1", "m1"), new Utilisateur("l2", "m2"), new Utilisateur("l3", "m3"));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.securiteService).listerUtilisateurs();

		// ACT
		final ParameterizedTypeReference<Collection<Utilisateur>> typeRetour = new ParameterizedTypeReference<Collection<Utilisateur>>() {
		};
		final ResponseEntity<Collection<Utilisateur>> utilisateurs = this.getREST().exchange(this.getURL() + "/utilisateur", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Assert.assertNotNull(utilisateurs.getBody());
		Assert.assertEquals(utilisateurs.getBody().size(), toReturn.size());
		Assert.assertEquals(utilisateurs.getBody().iterator().next().getLogin(), toReturn.iterator().next().getLogin());
		Mockito.verify(this.securiteService).listerUtilisateurs();
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test02SauvegardeUtilisateur() {
		final String login = "monLogin";
		final String mdp = "monMdp";

		// ARRANGE
		Mockito.doNothing().when(this.securiteService).sauvegarderUtilisateur(Mockito.anyString(), Mockito.anyString());

		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("login", login, "mdp", mdp);
		final Map<String, Object> uriVars = new HashMap<String, Object>();

		// ACT
		this.getREST().postForObject(this.getURL() + "/utilisateur", requestParam, Void.class, uriVars);

		// ASSERT
		Mockito.verify(this.securiteService).sauvegarderUtilisateur(login, mdp);
		Mockito.verifyNoMoreInteractions(this.securiteService);

	}

	@Test
	public void test03SupprimerUtilisateur() {
		final String login = "monLogin";

		// ARRANGE
		final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(this.securiteService).supprimerUtilisateur(argumentCaptor.capture());

		// ACT
		final ResponseEntity<Void> response = this.getREST().exchange(this.getURL() + "/utilisateur/" + login, HttpMethod.DELETE, null, Void.class);

		// ASSERT
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(argumentCaptor.getValue(), login);
		Mockito.verify(this.securiteService).supprimerUtilisateur(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test04Login01Ok() {
		final String login = "monLogin";
		final String mdp = "monMdp";

		// ARRANGE
		Mockito.doNothing().when(this.securiteService).verifierUtilisateur(Mockito.anyString(), Mockito.anyString());

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("login", login, "mdp", mdp);
		final HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestParam);
		final ResponseEntity<Void> response = this.getREST().exchange(this.getURL() + "/dologin", HttpMethod.POST, request, Void.class);

		// ASSERT
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Mockito.verify(this.securiteService).verifierUtilisateur(login, mdp);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test04Login02Ko() {
		final String login = "monLogin";
		final String mdp = "monMdp";

		// ARRANGE
		Mockito.doThrow(new BusinessException(BusinessException.ERREUR_LOGIN)).when(this.securiteService).verifierUtilisateur(Mockito.anyString(),
				Mockito.anyString());

		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("login", login, "mdp", mdp);
		final Map<String, Object> uriVars = new HashMap<String, Object>();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/dologin", requestParam, Void.class, uriVars);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		Assert.assertEquals(HttpStatus.FORBIDDEN, ((HttpClientErrorException) thrown).getStatusCode());
		Mockito.verify(this.securiteService).verifierUtilisateur(login, mdp);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test05Logout() {

		// ARRANGE

		// ACT
		this.getREST().getForObject(this.getURL() + "/dologout", Void.class);

		// ASSERT
		// Nothing to do
	}

	@Test
	public void test06SauvegardeUtilisateurKo() {
		final String login = "mon.Login";
		final String mdp = "monMdp";

		// ARRANGE
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("login", login, "mdp", mdp);
		final Map<String, Object> uriVars = new HashMap<String, Object>();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/utilisateur", requestParam, Void.class, uriVars);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertTrue(thrown instanceof HttpStatusCodeException);
		Assert.assertTrue(((HttpStatusCodeException) thrown).getResponseBodyAsString().contains("login"));
		Mockito.verifyNoMoreInteractions(this.securiteService);

	}

	@Test
	public void test07DeverrouilleUtilisateur() {
		final String login = "mon.Login";

		// ARRANGE
		Mockito.doNothing().when(this.securiteService).deverrouillerUtilisateur(Mockito.anyString());

		// ACT
		this.getREST().put(this.getURL() + "/utilisateur/" + login + "/deverrouille", null);

		// ASSERT
		Mockito.verify(this.securiteService).deverrouillerUtilisateur(login);
	}

	@Test
	public void test09ResetPassword() {
		final String loginToReset = "monLoginToReset";

		// ARRANGE
		Mockito.doNothing().when(this.securiteService).reinitialiserMotDePasse(loginToReset);

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("login", loginToReset);
		final HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(requestParam);
		final ResponseEntity<Void> response = this.getREST().exchange(this.getURL() + "/utilisateur/" + loginToReset + "/reset", HttpMethod.PUT,
				request, Void.class);

		// ASSERT
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Mockito.verify(this.securiteService).reinitialiserMotDePasse(loginToReset);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test10ChangementMdp() {
		final String login = "monLogin";
		final String mdp = "monMdp";
		final Utilisateur utilisateur = new Utilisateur(login, mdp);

		// ARRANGE
		Mockito.doReturn(utilisateur).when(this.securiteService).chargerUtilisateurReadOnly(Mockito.anyString());
		Mockito.doNothing().when(this.securiteService).sauvegarderUtilisateur(Mockito.anyString(), Mockito.anyString());

		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("mdp", mdp);
		final Map<String, Object> uriVars = new HashMap<String, Object>();

		// ACT
		this.getREST().postForObject(this.getURL() + "/utilisateur/" + login + "/changeMdp", requestParam, Void.class, uriVars);

		// ASSERT
		Mockito.verify(this.securiteService).sauvegarderUtilisateur(login, mdp);
		Mockito.verify(this.securiteService).chargerUtilisateurReadOnly(login);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test11ChangementMdpUtilisateurInexistant() {
		final String login = "monLogin";
		final String mdp = "monMdp";
		final Utilisateur utilisateur = null;

		// ARRANGE
		Mockito.doReturn(utilisateur).when(this.securiteService).chargerUtilisateurReadOnly(Mockito.anyString());
		Mockito.doNothing().when(this.securiteService).sauvegarderUtilisateur(Mockito.anyString(), Mockito.anyString());

		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("mdp", mdp);
		final Map<String, Object> uriVars = new HashMap<String, Object>();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/utilisateur/" + login + "/changeMdp", requestParam, Void.class, uriVars);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertTrue(thrown instanceof HttpStatusCodeException);
		Assert.assertEquals(((HttpStatusCodeException) thrown).getStatusCode(), HttpStatus.NOT_FOUND);
		Mockito.verify(this.securiteService).chargerUtilisateurReadOnly(login);
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

	@Test
	public void test99BugSuppressionUtilisateurDontLoginContientUnPoint() {
		final String login = "mon.login";
		final String loginObtenu = "mon.login".substring(0, login.indexOf("."));

		// ARRANGE
		final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(this.securiteService).supprimerUtilisateur(argumentCaptor.capture());

		// ACT
		final ResponseEntity<Void> response = this.getREST().exchange(this.getURL() + "/utilisateur/" + login, HttpMethod.DELETE, null, Void.class);

		// ASSERT
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(argumentCaptor.getValue(), loginObtenu);
		Mockito.verify(this.securiteService).supprimerUtilisateur(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(this.securiteService);
	}

}
