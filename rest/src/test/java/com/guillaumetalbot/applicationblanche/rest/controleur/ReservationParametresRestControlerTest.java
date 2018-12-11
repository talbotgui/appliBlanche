package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;

public class ReservationParametresRestControlerTest extends BaseTestClass {

	@Test
	public void test01Chambre01Lister() {

		// ARRANGE
		final List<Chambre> toReturn = Arrays.asList(new Chambre("CH1"), new Chambre("CH2"), new Chambre("CH3"), new Chambre("CH4"));
		Mockito.doReturn(toReturn).when(this.reservationParametresService).listerChambres();

		// ACT
		final ParameterizedTypeReference<Collection<Chambre>> typeRetour = new ParameterizedTypeReference<Collection<Chambre>>() {
		};
		final ResponseEntity<Collection<Chambre>> chambres = this.getREST().exchange(this.getURL() + "/v1/chambres", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationParametresService).listerChambres();
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertNotNull(chambres.getBody());
		Assert.assertEquals(chambres.getBody().size(), toReturn.size());
		Assert.assertEquals(chambres.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test01Chambre02SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Chambre.class, 1L);
		final Chambre chambre = new Chambre("C1");
		Mockito.doReturn(refRetournee).when(this.reservationParametresService).sauvegarderChambre(Mockito.any(Chambre.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/chambres", chambre, String.class);

		// ASSERT
		Mockito.verify(this.reservationParametresService).sauvegarderChambre(Mockito.any(Chambre.class));
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test01Chambre03SauvegarderKo() {

		// ARRANGE
		final Chambre chambre = new Chambre("");

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/chambres", chambre, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test01Chambre04Supprimer() {

		// ARRANGE
		final String ref = Entite.genererReference(Chambre.class, 2L);
		Mockito.doNothing().when(this.reservationParametresService).supprimerChambre(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/chambres/" + ref);

		// ASSERT
		Mockito.verify(this.reservationParametresService).supprimerChambre(ref);
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test02Produits01Lister() {

		// ARRANGE
		final List<Produit> toReturn = Arrays.asList(new Produit("P1", 1.0, "c1"), new Produit("P2", 2.0, "c2"), new Produit("P3", 3.0, "c3"));
		Mockito.doReturn(toReturn).when(this.reservationParametresService).listerProduits();

		// ACT
		final ParameterizedTypeReference<Collection<Produit>> typeRetour = new ParameterizedTypeReference<Collection<Produit>>() {
		};
		final ResponseEntity<Collection<Produit>> produits = this.getREST().exchange(this.getURL() + "/v1/produits", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationParametresService).listerProduits();
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertNotNull(produits.getBody());
		Assert.assertEquals(produits.getBody().size(), toReturn.size());
		Assert.assertEquals(produits.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test02Produits02Supprimer() {
		final String ref = Entite.genererReference(Produit.class, 1L);

		// ARRANGE
		Mockito.doNothing().when(this.reservationParametresService).supprimerProduit(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/produits/" + ref);

		// ASSERT
		Mockito.verify(this.reservationParametresService).supprimerProduit(ref);
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test02Produits03SauvegarderKoCouleur() {

		// ARRANGE
		final Produit produit = new Produit("nomP", 1.99, null);

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/produits", produit, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test02Produits03SauvegarderKoNom() {

		// ARRANGE
		final Produit produit = new Produit(null, 1.99, "rouge");

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/produits", produit, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test02Produits03SauvegarderKoPrix() {

		// ARRANGE
		final Produit produit = new Produit("nomP", null, "rouge");

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/produits", produit, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test02Produits03SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Produit.class, 1L);
		final Produit produit = new Produit("nomP", 1.99, "rouge");
		Mockito.doReturn(refRetournee).when(this.reservationParametresService).sauvegarderProduit(Mockito.any(Produit.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/produits", produit, String.class);

		// ASSERT
		Mockito.verify(this.reservationParametresService).sauvegarderProduit(Mockito.any(Produit.class));
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Formule01Lister() {

		// ARRANGE
		final List<Formule> toReturn = Arrays.asList(new Formule("F1", 1.2), new Formule("F2", 2.3), new Formule("F3", 2.1), new Formule("F4", 4.3));
		Mockito.doReturn(toReturn).when(this.reservationParametresService).listerFormules();

		// ACT
		final ParameterizedTypeReference<Collection<Formule>> typeRetour = new ParameterizedTypeReference<Collection<Formule>>() {
		};
		final ResponseEntity<Collection<Formule>> formuless = this.getREST().exchange(this.getURL() + "/v1/formules", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationParametresService).listerFormules();
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertNotNull(formuless.getBody());
		Assert.assertEquals(formuless.getBody().size(), toReturn.size());
		Assert.assertEquals(formuless.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test03Formule02SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Formule.class, 1L);
		final Formule formule = new Formule("F1", 2.1);
		Mockito.doReturn(refRetournee).when(this.reservationParametresService).sauvegarderFormule(Mockito.any(Formule.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/formules", formule, String.class);

		// ASSERT
		Mockito.verify(this.reservationParametresService).sauvegarderFormule(Mockito.any(Formule.class));
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Formule03SauvegarderKo() {

		// ARRANGE
		final Formule formule = new Formule();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/formules", formule, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test03Formule04Supprimer() {

		// ARRANGE
		final String ref = Entite.genererReference(Formule.class, 2L);
		Mockito.doNothing().when(this.reservationParametresService).supprimerFormule(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/formules/" + ref);

		// ASSERT
		Mockito.verify(this.reservationParametresService).supprimerFormule(ref);
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test03Option01Lister() {

		// ARRANGE
		final List<Option> toReturn = Arrays.asList(new Option("F1", 1.2, false, true), new Option("F2", 2.3, false, true),
				new Option("F3", 2.1, false, true), new Option("F4", 4.3, false, true));
		Mockito.doReturn(toReturn).when(this.reservationParametresService).listerOptions();

		// ACT
		final ParameterizedTypeReference<Collection<Option>> typeRetour = new ParameterizedTypeReference<Collection<Option>>() {
		};
		final ResponseEntity<Collection<Option>> optionss = this.getREST().exchange(this.getURL() + "/v1/options", HttpMethod.GET, null, typeRetour);

		// ASSERT
		Mockito.verify(this.reservationParametresService).listerOptions();
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertNotNull(optionss.getBody());
		Assert.assertEquals(optionss.getBody().size(), toReturn.size());
		Assert.assertEquals(optionss.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test03Option02SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Option.class, 1L);
		final Option option = new Option("F1", 2.1, false, true);
		Mockito.doReturn(refRetournee).when(this.reservationParametresService).sauvegarderOption(Mockito.any(Option.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/options", option, String.class);

		// ASSERT
		Mockito.verify(this.reservationParametresService).sauvegarderOption(Mockito.any(Option.class));
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Option03SauvegarderKo() {

		// ARRANGE
		final Option option = new Option();

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/options", option, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test03Option04Supprimer() {

		// ARRANGE
		final String ref = Entite.genererReference(Option.class, 2L);
		Mockito.doNothing().when(this.reservationParametresService).supprimerOption(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/options/" + ref);

		// ASSERT
		Mockito.verify(this.reservationParametresService).supprimerOption(ref);
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
	}

	@Test
	public void test04MoyenDePaiement01Lister() {

		// ARRANGE
		final List<MoyenDePaiement> toReturn = Arrays.asList(new MoyenDePaiement("1", 1.0), new MoyenDePaiement("2", 1.0),
				new MoyenDePaiement("3", 1.0), new MoyenDePaiement("4", 1.0));
		Mockito.doReturn(toReturn).when(this.reservationParametresService).listerMoyensDePaiement();

		// ACT
		final ParameterizedTypeReference<Collection<MoyenDePaiement>> typeRetour = new ParameterizedTypeReference<Collection<MoyenDePaiement>>() {
		};
		final ResponseEntity<Collection<MoyenDePaiement>> mdp = this.getREST().exchange(this.getURL() + "/v1/moyensDePaiement", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationParametresService).listerMoyensDePaiement();
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertNotNull(mdp.getBody());
		Assert.assertEquals(mdp.getBody().size(), toReturn.size());
		Assert.assertEquals(mdp.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test04MoyenDePaiement02SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(MoyenDePaiement.class, 1L);
		final MoyenDePaiement mdp = new MoyenDePaiement("1", 1.0);
		Mockito.doReturn(refRetournee).when(this.reservationParametresService).sauvegarderMoyenDePaiement(Mockito.any(MoyenDePaiement.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/moyensDePaiement", mdp, String.class);

		// ASSERT
		Mockito.verify(this.reservationParametresService).sauvegarderMoyenDePaiement(Mockito.any(MoyenDePaiement.class));
		Mockito.verifyNoMoreInteractions(this.reservationParametresService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}
}
