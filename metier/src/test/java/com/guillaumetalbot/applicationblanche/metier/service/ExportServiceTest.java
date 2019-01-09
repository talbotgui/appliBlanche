package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.Files;
import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExportServiceTest {

	@Autowired
	private ExportService exportService;

	private Reservation creerGrandeReservation() {
		final Reservation reservation = this.creerPetiteReservation();
		reservation.setOptions(new HashSet<>());
		for (int i = 0; i < 10; i++) {
			reservation.getOptions().add(new Option("o", Double.valueOf(i), false, true));
		}
		reservation.setPaiements(new HashSet<>());
		for (int i = 0; i < 30; i++) {
			reservation.getPaiements().add(new Paiement(LocalDate.now(), 20.50, new MoyenDePaiement("Liquide", 0.0), null));
		}
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
		return reservation;
	}

	@Test
	public void test01CasSimple() throws IOException {
		//
		final Reservation reservation = this.creerPetiteReservation();
		final Double montantTotal = 100D;

		//
		final byte[] flux = this.exportService.genererPdfFactureReservation(reservation, montantTotal);

		//
		Assert.assertNotNull(flux);
		Files.write(flux, new File("target/test01CasSimple.pdf"));
	}

	@Test
	public void test02CasRiche() throws IOException {
		//
		final Reservation reservation = this.creerGrandeReservation();
		final Double montantTotal = 100D;

		//
		final byte[] flux = this.exportService.genererPdfFactureReservation(reservation, montantTotal);

		//
		Assert.assertNotNull(flux);
		Files.write(flux, new File("target/test01CasRiche.pdf"));
	}

}
