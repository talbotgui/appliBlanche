package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
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

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;
import com.guillaumetalbot.applicationblanche.metier.dto.FactureDto;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
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

	private Reservation creerReservationFacturable() {
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
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 199.0, mdp, reservation));
		return reservation;
	}

	@Test
	public void test01Facturer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Reservation reservation = this.creerReservationFacturable();

		//
		final FactureDto factureDto = this.factureService.facturer(reservation.getReference());

		//
		Assert.assertNotNull(factureDto);
		Assert.assertNotNull(factureDto.getPdf());
		// formule x nbNuit + options x facteur
		// 200 x 3 + 100x3 + 10x6 + 1x2
		Assert.assertEquals((Double) 962.0, factureDto.getMontantTotal());
		Assert.assertEquals((Double) 363.0, factureDto.getMontantRestantDu());
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from FACTURE", Long.class));
	}

	@Test
	public void test02FacturerDeuxFoisUneReservation() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Reservation reservation = this.creerReservationFacturable();
		this.factureService.facturer(reservation.getReference());

		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 363.0, mdp, reservation));

		//
		final FactureDto secondeFactureDto = this.factureService.facturer(reservation.getReference());

		//
		Assert.assertNotNull(secondeFactureDto);
		Assert.assertNotNull(secondeFactureDto.getPdf());
		Assert.assertEquals((Double) 962.0, secondeFactureDto.getMontantTotal());
		Assert.assertEquals((Double) 0.0, secondeFactureDto.getMontantRestantDu());
		Assert.assertEquals((Long) 2L, jdbc.queryForObject("select count(*) from FACTURE", Long.class));
	}

	@Test
	public void test03FacturerAvecTropPercu() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Reservation reservation = this.creerReservationFacturable();
		this.factureService.facturer(reservation.getReference());

		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 301.0, mdp, reservation));
		this.factureService.facturer(reservation.getReference());

		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 63.0, mdp, reservation));

		//
		final FactureDto secondeFactureDto = this.factureService.facturer(reservation.getReference());

		//
		Assert.assertNotNull(secondeFactureDto);
		Assert.assertNotNull(secondeFactureDto.getPdf());
		Assert.assertEquals((Double) 962.0, secondeFactureDto.getMontantTotal());
		Assert.assertEquals((Double) (0.0 - 1.0), secondeFactureDto.getMontantRestantDu());
		Assert.assertEquals((Long) 3L, jdbc.queryForObject("select count(*) from FACTURE", Long.class));
	}

	@Test
	public void test04FacturerUneReservationInexistante() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String referenceReservation = Entite.genererReference(Reservation.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.factureService.facturer(referenceReservation);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from FACTURE", Long.class));
	}
}
