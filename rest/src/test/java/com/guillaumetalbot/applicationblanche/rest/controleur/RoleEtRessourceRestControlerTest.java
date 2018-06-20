package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.rest.controleur.dto.PageablePourLesTest;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.ControlerTestUtil;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.JwtIntegrationWebTest;

public class RoleEtRessourceRestControlerTest extends JwtIntegrationWebTest {

	@Test
	public void test01GetListeRole() {
		final List<Role> toReturn = Arrays.asList(new Role("R1"), new Role("R2"), new Role("R3"));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.securiteService).listerRoles();

		// ACT
		final ParameterizedTypeReference<Collection<Role>> typeRetour = new ParameterizedTypeReference<Collection<Role>>() {
		};
		final ResponseEntity<Collection<Role>> roles = this.getREST().exchange(this.getURL() + "/v1/roles", HttpMethod.GET, null, typeRetour);

		// ASSERT
		Mockito.verify(this.securiteService).listerRoles();
		Mockito.verifyNoMoreInteractions(this.securiteService);
		Assert.assertNotNull(roles.getBody());
		Assert.assertEquals(roles.getBody().size(), toReturn.size());
		Assert.assertEquals(roles.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test02GetListeRessource() {
		final Page<Ressource> toReturn = new PageablePourLesTest<>(Arrays.asList(new Ressource("Re1"), new Ressource("Re2"), new Ressource("Re3")));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.securiteService).listerRessources(Mockito.any(Pageable.class));

		// ACT
		final ParameterizedTypeReference<PageablePourLesTest<Ressource>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<Ressource>>() {
		};
		final ResponseEntity<PageablePourLesTest<Ressource>> reponse = this.getREST().exchange(this.getURL() + "/v1/ressources", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.securiteService).listerRessources(Mockito.any(Pageable.class));
		Mockito.verifyNoMoreInteractions(this.securiteService);
		Assert.assertNotNull(reponse.getBody());
		Assert.assertEquals(reponse.getBody().getContent().size(), toReturn.getContent().size());
	}

	@Test
	public void test03SauvegardeRole01dOk() {
		final String nomRole = "R1";

		// ARRANGE
		Mockito.doNothing().when(this.securiteService).sauvegarderRole(nomRole);

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("nom", nomRole);
		final Map<String, Object> uriVars = new HashMap<String, Object>();
		this.getREST().postForObject(this.getURL() + "/v1/roles", requestParam, Void.class, uriVars);

		// ASSERT
		Mockito.verify(this.securiteService).sauvegarderRole(nomRole);
		Mockito.verifyNoMoreInteractions(this.clientService);
	}
}
