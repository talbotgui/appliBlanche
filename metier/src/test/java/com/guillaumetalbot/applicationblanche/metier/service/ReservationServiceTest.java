package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceTest.class);

	@Autowired
	private DataSource dataSource;

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

	private String sauvegarderUneReservation(final String client, final String refChambre, final int deltaDateDebut, final int deltaDateFin) {
		final Chambre c = new Chambre();
		c.setReference(refChambre);
		final LocalDate dateDebut = LocalDate.now().plus(deltaDateDebut, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(deltaDateFin, ChronoUnit.DAYS);
		return this.reservationService.sauvegarderReservation(new Reservation(client, c, dateDebut, dateFin));
	}

	@Test
	public void test01Produit01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals(Entite.genererReference(Produit.class, 1L), ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test01Produit02CreationKoProduitEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test01Produit03Lister() {
		//
		final List<String> nomDesProduits = Arrays.asList("nom1", "nom2", "nom3");
		this.reservationService.sauvegarderProduit(new Produit(nomDesProduits.get(2), 0.99, "rouge"));
		this.reservationService.sauvegarderProduit(new Produit(nomDesProduits.get(0), 1.0, "bleu"));
		this.reservationService.sauvegarderProduit(new Produit(nomDesProduits.get(1), 2.5, "rouge"));

		//
		final Collection<Produit> produits = this.reservationService.listerProduits();

		//
		Assert.assertNotNull(produits);
		Assertions.assertThat(produits.stream().map((p) -> p.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesProduits);
	}

	@Test
	public void test01Produit03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String ref = this.reservationService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		this.reservationService.supprimerProduit(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test02Chambre01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationService.sauvegarderChambre(new Chambre("nom1"));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test02Chambre02CreationKoChambreEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationService.sauvegarderChambre(new Chambre("nom1"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderChambre(new Chambre("nom1"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test02Chambre03Lister() {
		//
		final List<String> nomDesChambres = Arrays.asList("nom1", "nom2", "nom3");
		this.reservationService.sauvegarderChambre(new Chambre(nomDesChambres.get(2)));
		this.reservationService.sauvegarderChambre(new Chambre(nomDesChambres.get(0)));
		this.reservationService.sauvegarderChambre(new Chambre(nomDesChambres.get(1)));

		//
		final Collection<Chambre> chambres = this.reservationService.listerChambres();

		//
		Assert.assertNotNull(chambres);
		Assertions.assertThat(chambres.stream().map((c) -> c.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesChambres);
	}

	@Test
	public void test03Reservation01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom1"));
		final String client = "client ";

		//
		final String ref = this.sauvegarderUneReservation(client, refChambre, -1, 2);

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from RESERVATION where client=?", new Object[] { client.trim() }, Long.class));
	}

	@Test
	public void test03Reservation02CreationKoChambreInexistante() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = Entite.genererReference(Chambre.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.sauvegarderUneReservation("client", refChambre, -1, 2);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test03Reservation03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom1"));
		final String ref = this.sauvegarderUneReservation("client", refChambre, -1, 2);

		//
		this.reservationService.supprimerReservation(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from RESERVATION", Long.class));
	}
}
