package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FactureServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(FactureServiceTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private FactureService factureService;

	@Autowired
	private ReservationParametresService reservationParametresService;

	@Autowired
	private ReservationService reservationService;

	@Before
	public void before() throws IOException, URISyntaxException {
		LOG.info("---------------------------------------------------------");

		// Destruction des données
		final Collection<String> strings = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("db/dataPurge.sql").toURI()));
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		LOG.info("Execute SQL : {}", strings);
		jdbc.batchUpdate(strings.toArray(new String[strings.size()]));

	}

	@Test
	public void test01Facturer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();

		this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final Chambre c = this.reservationParametresService.listerChambres().iterator().next();

		this.reservationParametresService.sauvegarderFormule(new Formule("formule0", 200.0));
		final Formule f = this.reservationParametresService.listerFormules().iterator().next();

		this.reservationParametresService.sauvegarderOption(new Option("o1", 100.0, true, false));
		this.reservationParametresService.sauvegarderOption(new Option("o2", 10.0, true, true));
		this.reservationParametresService.sauvegarderOption(new Option("o3", 1.0, false, true));
		final Collection<Option> toutesLesOptions = this.reservationParametresService.listerOptions();

		final LocalDate dateDebut = LocalDate.now().plus(-3, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now();
		final String refReservation = this.reservationService
				.sauvegarderReservation(new Reservation("client1", c, dateDebut, dateFin, f, toutesLesOptions));
		this.reservationService.changeEtatReservation(refReservation, EtatReservation.EN_COURS);

		final Reservation reservation = this.reservationService.chargerReservation(refReservation);
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 400.0, mdp, reservation));
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 99.0, mdp, reservation));

		//
		final FactureDto factureDto = this.factureService.facturer(refReservation);

		//
		Assert.assertNotNull(factureDto);
		Assert.assertNotNull(factureDto.getPdf());
		Assert.assertEquals((Double) 63.0, factureDto.getMontantRestantDu());
		Assert.assertEquals((Double) 562.0, factureDto.getMontantTotal());
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from FACTURE", Long.class));
	}
}
