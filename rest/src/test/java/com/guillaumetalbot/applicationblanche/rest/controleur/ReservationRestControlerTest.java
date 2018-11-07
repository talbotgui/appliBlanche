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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public class ReservationRestControlerTest extends BaseTestClass {

	@Test
	public void test01Reservation01Lister() {

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
		final ResponseEntity<Collection<Reservation>> reservations = this.getREST()
				.exchange(this.getURL() + "/v1/reservations?dateDebut={dateDebut}&dateFin={dateFin}", HttpMethod.GET, null, typeRetour, uriVars);

		// ASSERT
		Mockito.verify(this.reservationService).rechercherReservations(debut, fin);
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertNotNull(reservations.getBody());
		Assert.assertEquals(reservations.getBody().size(), toReturn.size());
	}

	@Test
	public void test01Reservation02Supprimer() {
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
	public void test01Reservation03SauvegarderKoChambre() {

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
	public void test01Reservation03SauvegarderKoChambreReference() {

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
	public void test01Reservation03SauvegarderKoClient() {

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
	public void test01Reservation03SauvegarderKoDatesAbsentes() {

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
	public void test01Reservation03SauvegarderKoDatesIncoherentes() {

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
	public void test01Reservation03SauvegarderOk() {

		// ARRANGE
		final String refRetournee = Entite.genererReference(Produit.class, 1L);
		final Chambre chambre = new Chambre("C1");
		chambre.setReference(Entite.genererReference(Chambre.class, 1L));
		final Formule formule = new Formule();
		formule.setReference(Entite.genererReference(Formule.class, 1L));
		final Reservation reservation = new Reservation("client", chambre, LocalDate.now(), LocalDate.now(), formule);
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderReservation(Mockito.any(Reservation.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/reservations", reservation, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderReservation(Mockito.any(Reservation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}

	@Test
	public void test01Reservations04ChangerEtat() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		Mockito.doNothing().when(this.reservationService).changeEtatReservation(Mockito.anyString(), Mockito.any(EtatReservation.class));

		// ACT
		this.getREST().put(this.getURL() + "/v1/reservations/" + refReservation + "/etat?etat=EN_COURS", null);

		// ASSERT
		Mockito.verify(this.reservationService).changeEtatReservation(Mockito.anyString(), Mockito.any(EtatReservation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test01Reservations05ListerReservationsCourantes() {

		// ARRANGE
		final List<Reservation> toReturn = Arrays.asList(new Reservation("c1", new Chambre(), LocalDate.now(), LocalDate.now()),
				new Reservation("c1", new Chambre(), LocalDate.now(), LocalDate.now()));
		Mockito.doReturn(toReturn).when(this.reservationService).rechercherReservationsCourantes();

		// ACT
		final ParameterizedTypeReference<Collection<Reservation>> typeRetour = new ParameterizedTypeReference<Collection<Reservation>>() {
		};
		final ResponseEntity<Collection<Reservation>> reservations = this.getREST().exchange(this.getURL() + "/v1/reservations/courantes",
				HttpMethod.GET, null, typeRetour);

		// ASSERT
		Assert.assertNotNull(reservations);
		Assert.assertEquals(toReturn.size(), reservations.getBody().size());
		Mockito.verify(this.reservationService).rechercherReservationsCourantes();
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test02Consommation01Lister() {

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
	public void test02Consommation02Supprimer() {
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String ref = Entite.genererReference(Consommation.class, 1L);

		// ARRANGE
		Mockito.doNothing().when(this.reservationService).supprimerConsommation(refReservation, ref);

		// ACT
		this.getREST().delete(this.getURL() + "/v1/reservations/" + refReservation + "/consommations/" + ref);

		// ASSERT
		Mockito.verify(this.reservationService).supprimerConsommation(refReservation, ref);
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test02Consommation04ModifierQuantite() {
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String ref = Entite.genererReference(Consommation.class, 1L);
		final Integer quantite = -1;

		// ARRANGE
		Mockito.doNothing().when(this.reservationService).modifierQuantiteConsommation(refReservation, ref, quantite);

		// ACT
		this.getREST().put(this.getURL() + "/v1/reservations/" + refReservation + "/consommations/" + ref + "?quantite=" + quantite, null);

		// ASSERT
		Mockito.verify(this.reservationService).modifierQuantiteConsommation(refReservation, ref, quantite);
		Mockito.verifyNoMoreInteractions(this.reservationService);
	}

	@Test
	public void test02Consommations03SauvegarderKoProduit() {

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
	public void test02Consommations03SauvegarderKoQuantite() {

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
	public void test02Consommations03SauvegarderOk() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String refRetournee = Entite.genererReference(Consommation.class, 1L);
		final Reservation reservation = new Reservation("client", null, LocalDate.now(), LocalDate.now());
		final Produit produit = new Produit("P1", 1.99, "rouge");
		produit.setReference(Entite.genererReference(Produit.class, 1L));
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
	public void test02Consommations03SauvegarderOkSansObjetReservation() {

		// ARRANGE
		final String refReservation = Entite.genererReference(Reservation.class, 1L);
		final String refRetournee = Entite.genererReference(Consommation.class, 1L);
		final Produit produit = new Produit("P1", 1.99, "rouge");
		produit.setReference(Entite.genererReference(Produit.class, 1L));
		final Consommation conso = new Consommation(null, produit, null, 1);
		Mockito.doReturn(refRetournee).when(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));

		// ACT
		final String ref = this.getREST().postForObject(this.getURL() + "/v1/reservations/" + refReservation + "/consommations", conso, String.class);

		// ASSERT
		Mockito.verify(this.reservationService).sauvegarderConsommation(Mockito.any(Consommation.class));
		Mockito.verifyNoMoreInteractions(this.reservationService);
		Assert.assertEquals(ref, '"' + refRetournee + '"');
	}
}
