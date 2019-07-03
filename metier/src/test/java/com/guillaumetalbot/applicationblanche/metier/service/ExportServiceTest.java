package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.io.Files;
import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@SpringBootTest(classes = SpringApplicationForTests.class)
public class ExportServiceTest {

	@Autowired
	private ExportService exportService;

	private Reservation creerGrandeReservation() {
		final Reservation reservation = this.creerPetiteReservation();

		final Set<Option> listeOption = new HashSet<>();
		for (int i = 0; i < 20; i++) {
			listeOption.add(new Option("o", Double.valueOf(i), false, true));
		}
		reservation.setOptions(listeOption);

		final Set<Paiement> listePaiement = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			final String moyen = i % 2 == 0 ? "Liquide" : "CB";
			listePaiement.add(new Paiement(LocalDate.now(), 20.50, new MoyenDePaiement(moyen, 0.0), null));
		}
		reservation.setPaiements(listePaiement);

		final Set<Consommation> listeConsomation = new HashSet<>();
		for (int i = 0; i < 30; i++) {
			final String nb = (i < 10 ? "0" : "") + i;
			final LocalDate dateCreation = LocalDate.now().minus(i / 5, ChronoUnit.DAYS);
			listeConsomation.add(new Consommation(reservation, new Produit("Produit" + nb, 2.1123, ""), 2.3214, i * 3, dateCreation));
		}
		reservation.setConsommations(listeConsomation);

		return reservation;
	}

	private Reservation creerPetiteReservation() {
		final Chambre chambre = new Chambre("chambre1");
		final LocalDate dateDebut = LocalDate.now();
		final LocalDate dateFin = LocalDate.now().plus(1, ChronoUnit.DAYS);
		final Reservation reservation = new Reservation("M. Client Jean", chambre, dateDebut, dateFin);
		reservation.setReference(Entite.genererReference(Reservation.class, 1L));
		reservation.setFormule(new Formule("formule_A", 70.0));
		reservation.setOptions(new HashSet<>(Arrays.asList(new Option("o1", 2.0, false, true), new Option("o2", 3.0, true, false))));
		reservation.setPaiements(new HashSet<>(Arrays.asList(//
				new Paiement(LocalDate.now(), 20.50, new MoyenDePaiement("Liquide", 0.0), null),
				new Paiement(LocalDate.now(), 19.50, new MoyenDePaiement("CB", 0.0), null))));
		reservation.setConsommations(new HashSet<>(Arrays.asList(//
				new Consommation(reservation, new Produit("Produit1", 2.0, ""), 2.25, 7, LocalDate.now()),
				new Consommation(reservation, new Produit("Produit2", 4.0, ""), 4.5, 1, LocalDate.now()),
				new Consommation(reservation, new Produit("Produit3", 5.0, ""), 9.0, 2, LocalDate.now()))));
		return reservation;
	}

	@Test
	public void test01CasSimple() throws IOException {
		//
		final Reservation reservation = this.creerPetiteReservation();

		//
		final byte[] flux = this.exportService.genererPdfFactureReservation(reservation, 1L);

		//
		Assertions.assertNotNull(flux);
		Files.write(flux, new File("target/test01CasSimple.pdf"));
	}

	@Test
	public void test02CasRiche() throws IOException {
		//
		final Reservation reservation = this.creerGrandeReservation();

		//
		final byte[] flux = this.exportService.genererPdfFactureReservation(reservation, 10012L);

		//
		Assertions.assertNotNull(flux);
		Files.write(flux, new File("target/test01CasRiche.pdf"));
	}

}
