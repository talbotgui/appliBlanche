package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;
import com.guillaumetalbot.applicationblanche.rest.application.RestApplication;
import com.guillaumetalbot.applicationblanche.rest.controleur.dto.PageablePourLesTest;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.ControlerTestUtil;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.JwtIntegrationWebTest;

public class ClientRestControlerTest extends JwtIntegrationWebTest {

	@Test
	public void test01GetListeClient01NonPaginee() {

		final List<Client> toReturn = Arrays.asList(new Client("C1"), new Client("C2"), new Client("C3"));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.clientService).listerClients();

		// ACT
		final ParameterizedTypeReference<Collection<Client>> typeRetour = new ParameterizedTypeReference<Collection<Client>>() {
		};
		final ResponseEntity<Collection<Client>> clients = this.getREST().exchange(this.getURL() + "/v1/clients", HttpMethod.GET, null, typeRetour);

		// ASSERT
		Mockito.verify(this.clientService).listerClients();
		Mockito.verifyNoMoreInteractions(this.clientService);
		Assert.assertNotNull(clients.getBody());
		Assert.assertEquals(clients.getBody().size(), toReturn.size());
		Assert.assertEquals(clients.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test01GetListeClient02Paginee() {
		final PageablePourLesTest<ClientDto> toReturn = new PageablePourLesTest<>(Arrays.asList(new ClientDto("C1", "v", 1L, 1L, null),
				new ClientDto("C2", "v", 1L, 1L, null), new ClientDto("C3", "v", 1L, 1L, null)));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.clientService).listerClientsDto(Mockito.any(Pageable.class));

		// ACT
		final ParameterizedTypeReference<PageablePourLesTest<Client>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<Client>>() {
		};
		final ResponseEntity<PageablePourLesTest<Client>> clients = this.getREST().exchange(this.getURL() + "/v1/clients?pageNumber=1&pageSize=5",
				HttpMethod.GET, null, typeRetour);

		// ASSERT
		Mockito.verify(this.clientService).listerClientsDto(Mockito.any(Pageable.class));
		Mockito.verifyNoMoreInteractions(this.clientService);
		Assert.assertNotNull(clients.getBody());
		Assert.assertEquals(clients.getBody().getContent().size(), toReturn.getContent().size());
	}

	@Test
	public void test01GetListeClient03PagineeAvecErreurSurPageSize() {

		// ARRANGE

		// ACT
		final Throwable t = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<PageablePourLesTest<Client>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<Client>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/clients?pageNumber=1", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) t;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.clientService);
	}

	@Test
	public void test01GetListeClient03PagineeAvecErreurSurPageSizeNegatif() {

		// ARRANGE

		// ACT
		final Throwable t = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<PageablePourLesTest<Client>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<Client>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/clients?pageNumber=-1&pageSize=2", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) t;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.clientService);
	}

	@Test
	public void test01GetListeClient04PagineeAvecErreurSurPageNumber() {

		// ARRANGE

		// ACT
		final Throwable t = Assertions.catchThrowable(() -> {
			final ParameterizedTypeReference<PageablePourLesTest<Client>> typeRetour = new ParameterizedTypeReference<PageablePourLesTest<Client>>() {
			};
			this.getREST().exchange(this.getURL() + "/v1/clients?pageSize=1", HttpMethod.GET, null, typeRetour);
		});

		// ASSERT
		Assert.assertNotNull(t);
		Assert.assertEquals(t.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) t;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.clientService);
	}

	@Test
	public void test02GetClient01Details() {
		final String nomDossier = "D1";
		final String refClient = Entite.genererReference(Client.class, 1L);
		final Client toReturn = new Client("C1");
		toReturn.addDossier(new Dossier(nomDossier));

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.clientService).chargerClientAvecAdresseEtDossiersReadonly(refClient);

		// ACT
		final HttpEntity<?> headers = ControlerTestUtil.creerHeaders(RestApplication.MIME_JSON_DETAILS);
		final ResponseEntity<Client> client = this.getREST().exchange(this.getURL() + "/v1/clients/" + refClient, HttpMethod.GET, headers,
				Client.class);

		// ASSERT
		Mockito.verify(this.clientService).chargerClientAvecAdresseEtDossiersReadonly(refClient);
		Mockito.verifyNoMoreInteractions(this.clientService);
		Assert.assertNull(client.getBody().getId());
		Assert.assertNotNull(client.getBody().getDossiers());
		Assert.assertEquals(client.getBody().getDossiers().iterator().next().getNom(), nomDossier);
	}

	@Test
	public void test02GetClient01Standard() {
		final String refClient = Entite.genererReference(Client.class, 1L);
		final Client toReturn = new Client("C1");

		// ARRANGE
		Mockito.doReturn(toReturn).when(this.clientService).chargerClientReadonly(refClient);

		// ACT
		final Client client = this.getREST().getForObject(this.getURL() + "/v1/clients/" + refClient, Client.class);

		// ASSERT
		Mockito.verify(this.clientService).chargerClientReadonly(refClient);
		Mockito.verifyNoMoreInteractions(this.clientService);
		Assert.assertNull(client.getId());
		Assert.assertNull(client.getDossiers());
	}

	@Test
	public void test03SauvegardeClient01dOk() {
		final String refClient = Entite.genererReference(Client.class, 1L);
		final String nomClient = "C1";

		// ARRANGE
		Mockito.doReturn(refClient).when(this.clientService).sauvegarderClient(null, nomClient);

		// ACT
		final MultiValueMap<String, Object> requestParam = ControlerTestUtil.creeMapParamRest("nom", nomClient);
		final Map<String, Object> uriVars = new HashMap<String, Object>();
		final String refClientRetournee = this.getREST().postForObject(this.getURL() + "/v1/clients", requestParam, String.class, uriVars);

		// ASSERT
		Mockito.verify(this.clientService).sauvegarderClient(null, nomClient);
		Mockito.verifyNoMoreInteractions(this.clientService);
		Assert.assertEquals(refClientRetournee, refClient);
	}
}
