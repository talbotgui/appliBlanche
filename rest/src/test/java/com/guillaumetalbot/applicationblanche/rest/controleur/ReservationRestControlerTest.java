package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public class ReservationRestControlerTest extends BaseTestClass {

	@Test
	public void test01Chambre01Lister() {

		// ARRANGE
		final List<Chambre> toReturn = Arrays.asList(new Chambre("CH1"), new Chambre("CH2"), new Chambre("CH3"), new Chambre("CH4"));
		Mockito.doReturn(toReturn).when(this.reservationService).listerChambres();

		// ACT
		final ParameterizedTypeReference<Collection<Chambre>> typeRetour = new ParameterizedTypeReference<Collection<Chambre>>() {
		};
		final ResponseEntity<Collection<Chambre>> chambres = this.getREST().exchange(this.getURL() + "/v1/chambres", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationService).listerChambres();
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertNotNull(chambres.getBody());
		Assert.assertEquals(chambres.getBody().size(), toReturn.size());
		Assert.assertEquals(chambres.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test01Chambre02SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Chambre.class, 1L);
		final Chambre chambre = new Chambre("C1");
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderChambre(Mockito.any(Chambre.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/chambres", chambre, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderChambre(Mockito.any(Chambre.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
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
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test01Chambre04Supprimer() {

		// ARRANGE
		final String ref = Entite.genererReference(Chambre.class, 2L);
		Mockito.doNothing().when(this.reservationService).supprimerChambre(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/chambres/" + ref);

		// ASSERT
		Mockito.verify(this.reservationService).supprimerChambre(ref);
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test02Produits01Lister() {

		// ARRANGE
		final List<Produit> toReturn = Arrays.asList(new Produit("P1", 1.0, "c1"), new Produit("P2", 2.0, "c2"), new Produit("P3", 3.0, "c3"));
		Mockito.doReturn(toReturn).when(this.reservationService).listerProduits();

		// ACT
		final ParameterizedTypeReference<Collection<Produit>> typeRetour = new ParameterizedTypeReference<Collection<Produit>>() {
		};
		final ResponseEntity<Collection<Produit>> produits = this.getREST().exchange(this.getURL() + "/v1/produits", HttpMethod.GET, null,
				typeRetour);

		// ASSERT
		Mockito.verify(this.reservationService).listerProduits();
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertNotNull(produits.getBody());
		Assert.assertEquals(produits.getBody().size(), toReturn.size());
		Assert.assertEquals(produits.getBody().iterator().next().getNom(), toReturn.iterator().next().getNom());
	}

	@Test
	public void test02Produits02Supprimer() {
		final String ref = Entite.genererReference(Produit.class, 1L);

		// ARRANGE
		Mockito.doNothing().when(this.reservationService).supprimerProduit(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/produits/" + ref);

		// ASSERT
		Mockito.verify(this.reservationService).supprimerProduit(ref);
		Mockito.verifyNoMoreInteractions(this.reservationService);
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
		Mockito.verifyNoMoreInteractions(this.reservationService);
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
		Mockito.verifyNoMoreInteractions(this.reservationService);
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
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test02Produits03SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Produit.class, 1L);
		final Produit produit = new Produit("nomP", 1.99, "rouge");
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderProduit(Mockito.any(Produit.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/produits", produit, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderProduit(Mockito.any(Produit.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Consommation01Lister() {

		// ARRANGE
		final List<Consommation> toReturn = Arrays.asList(new Consommation(new Reservation(), new Produit(), 1.0, 1),
				new Consommation(new Reservation(), new Produit(), 2.0, 2), new Consommation(new Reservation(), new Produit(), 3.0, 3));
		final String referenceReservation = Entite.genererReference(Reservation.class, 1L);
		Mockito.doReturn(toReturn).when(this.reservationService).rechercherConsommationsDuneReservation(referenceReservation);

		// ACT
		final ParameterizedTypeReference<Collection<Produit>> typeRetour = new ParameterizedTypeReference<Collection<Produit>>() {
		};
		final String uri = "/v1/reservations/" + referenceReservation + "/consommations";
		final ResponseEntity<Collection<Produit>> produits = this.getREST().exchange(this.getURL() + uri, HttpMethod.GET, null, typeRetour);

		// ASSERT
		Mockito.verify(this.reservationService).rechercherConsommationsDuneReservation(referenceReservation);
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertNotNull(produits.getBody());
		Assert.assertEquals(produits.getBody().size(), toReturn.size());
	}

	@Test
	public void test03Consommation02Supprimer() {
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String ref = Entite.genererReference(Consommation.class, 1L);

		// ARRANGE
		Mockito.doNothing().when(this.reservationService).supprimerConsommation(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/reservations/" + refReservation + "/consommations/" + ref);

		// ASSERT
		Mockito.verify(this.reservationService).supprimerConsommation(ref);
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Consommations03SauvegarderKoProduit() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final Reservation reservation = new Reservation("client", null, LocalDate.now(), LocalDate.now());
		final Consommation conso = new Consommation(reservation, null, null, 1);

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations/" + refReservation + "/consommations", conso, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Consommations03SauvegarderKoQuantite() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final Reservation reservation = new Reservation("client", null, LocalDate.now(), LocalDate.now());
		final Produit produit = new Produit("P1", 1.99, "rouge");
		final Consommation conso = new Consommation(reservation, produit, null, null);

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations/" + refReservation + "/consommations", conso, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Consommations03SauvegarderOk() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String refRetournee = Entite.genererReference(Consommation.class, 1L);
		final Reservation reservation = new Reservation("client", null, LocalDate.now(), LocalDate.now());
		final Produit produit = new Produit("P1", 1.99, "rouge");
		final Consommation conso = new Consommation(reservation, produit, null, 1);
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/reservations/" + refReservation + "/consommations", conso, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Consommations03SauvegarderOkSansObjetReservation() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String refRetournee = Entite.genererReference(Consommation.class, 1L);
		final Produit produit = new Produit("P1", 1.99, "rouge");
		final Consommation conso = new Consommation(null, produit, null, 1);
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/reservations/" + refReservation + "/consommations", conso, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test03Reservation01Lister() {

		// ARRANGE
		final String dateDebut = "2019-02-10";
		final String dateFin = "2019-02-12";
		final LocalDate debut = LocalDate.parse(dateDebut, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		final LocalDate fin = LocalDate.parse(dateFin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		final List<Reservation> toReturn = Arrays.asList(new Reservation("c1", new Chambre(), LocalDate.now(), LocalDate.now()),
				new Reservation("c1", new Chambre(), LocalDate.now(), LocalDate.now()));
		Mockito.doReturn(toReturn).when(this.reservationService).rechercherReservations(debut, fin);

		// ACT
		final ParameterizedTypeReference<Collection<Reservation>> typeRetour = new ParameterizedTypeReference<Collection<Reservation>>() {
		};
		final Map<String, Object> uriVars = new HashMap<String, Object>();
		uriVars.put("dateDebut", debut);
		uriVars.put("dateFin", dateFin);
		final ResponseEntity<Collection<Reservation>> produits = this.getREST()
				.exchange(this.getURL() + "/v1/reservations?dateDebut={dateDebut}&dateFin={dateFin}", HttpMethod.GET, null, typeRetour, uriVars);

		// ASSERT
		Mockito.verify(this.reservationService).rechercherReservations(debut, fin);
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertNotNull(produits.getBody());
		Assert.assertEquals(produits.getBody().size(), toReturn.size());
	}

	@Test
	public void test03Reservation02Supprimer() {
		final String ref = Entite.genererReference(Reservation.class, 1L);

		// ARRANGE
		Mockito.doNothing().when(this.reservationService).supprimerReservation(ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/reservations/" + ref);

		// ASSERT
		Mockito.verify(this.reservationService).supprimerReservation(ref);
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderKoChambre() {

		// ARRANGE
		final Reservation reservation = new Reservation("client", null, LocalDate.now(), LocalDate.now());

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderKoChambreReference() {

		// ARRANGE
		final Chambre chambre = new Chambre("C1");
		final Reservation reservation = new Reservation("client", chambre, LocalDate.now(), LocalDate.now());

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderKoClient() {

		// ARRANGE
		final Chambre chambre = new Chambre("C1");
		chambre.setReference(Entite.genererReference(Chambre.class, 1L));
		final Reservation reservation = new Reservation("", chambre, LocalDate.now(), LocalDate.now());

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderKoDatesAbsentes() {

		// ARRANGE
		final Chambre chambre = new Chambre("C1");
		chambre.setReference(Entite.genererReference(Chambre.class, 1L));
		final Reservation reservation = new Reservation("client", chambre, null, null);

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderKoDatesIncoherentes() {

		// ARRANGE
		final Chambre chambre = new Chambre("C1");
		chambre.setReference(Entite.genererReference(Chambre.class, 1L));
		final LocalDate debut = LocalDate.now();
		final LocalDate fin = LocalDate.now().minus(2, ChronoUnit.DAYS);
		final Reservation reservation = new Reservation("client", chambre, debut, fin);

		// ACT
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);
		});

		// ASSERT
		Assert.assertNotNull(thrown);
		Assert.assertEquals(thrown.getClass(), HttpClientErrorException.class);
		final HttpClientErrorException e = (HttpClientErrorException) thrown;
		Assert.assertEquals(e.getRawStatusCode(), HttpStatus.BAD_REQUEST.value());
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test03Reservation03SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Produit.class, 1L);
		final Chambre chambre = new Chambre("C1");
		chambre.setReference(Entite.genererReference(Chambre.class, 1L));
		final Reservation reservation = new Reservation("client", chambre, LocalDate.now(), LocalDate.now());
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderReservation(Mockito.any(Reservation.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderReservation(Mockito.any(Reservation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}
}
