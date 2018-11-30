package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExportServiceTest {

	@Autowired
	private ExportService exportService;

	@Test
	public void test01CasSimple() throws IOException {
		//
		final Chambre chambre = new Chambre("chambre1");
		final LocalDate dateDebut = LocalDate.now();
		final LocalDate dateFin = LocalDate.now().plus(1, ChronoUnit.DAYS);
		final Reservation reservation = new Reservation("client", chambre, dateDebut, dateFin);
		reservation.setFormule(new Formule("formule_A", 70.0));
		reservation.setOptions(Arrays.asList(new Option("o1", 2.0, false, true), new Option("o2", 3.0, true, false)));
		final Double montantTotal = 100D;

		//
		final byte[] flux = this.exportService.genererPdfFactureReservation(reservation, montantTotal);

		//
		Assert.assertNotNull(flux);
		Files.write(flux, new File("target/test01CasSimple.pdf"));
	}

}
