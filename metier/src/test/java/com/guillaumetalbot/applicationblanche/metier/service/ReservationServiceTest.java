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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
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

	@Test
	public void test03Reservation04RechercherVide() {
		//
		final LocalDate dateDebut = LocalDate.now().plus(-30, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(+30, ChronoUnit.DAYS);
		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(dateDebut, dateFin);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(0, reservations.size());
	}

	@Test
	public void test03Reservation05RechercherTouteDedans() {
		//
		final LocalDate dateDebut = LocalDate.now().plus(-30, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(+30, ChronoUnit.DAYS);
		final String refChambre1 = this.reservationService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationService.sauvegarderChambre(new Chambre("nom2"));
		final String refChambre3 = this.reservationService.sauvegarderChambre(new Chambre("nom3"));
		this.sauvegarderUneReservation("client1", refChambre1, -10, -8);
		this.sauvegarderUneReservation("client2", refChambre1, -8, -6);
		this.sauvegarderUneReservation("client3", refChambre2, 0, 2);
		this.sauvegarderUneReservation("client3", refChambre3, 10, 12);

		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(dateDebut, dateFin);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(4, reservations.size());
	}

	@Test
	public void test03Reservation06RechercherAvecClassesEquivalence() {
		//
		final LocalDate dateDebut = LocalDate.now().plus(-30, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(+30, ChronoUnit.DAYS);
		final String refChambre1 = this.reservationService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationService.sauvegarderChambre(new Chambre("nom2"));
		final String refChambre3 = this.reservationService.sauvegarderChambre(new Chambre("nom3"));
		this.sauvegarderUneReservation("clientPassé", refChambre1, -10, -8);
		this.sauvegarderUneReservation("clientFutur", refChambre1, 4, 6);
		this.sauvegarderUneReservation("clientPasséAlaLimite", refChambre2, -33, -29);
		this.sauvegarderUneReservation("clientFuturAlaLimite", refChambre3, 29, 31);

		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(dateDebut, dateFin);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(4, reservations.size());
	}

	@Test
	public void test03Reservation07CreationKoDatesDejaPrises() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("C1"));
		this.sauvegarderUneReservation("client1", refChambre, 2, 4);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.sauvegarderUneReservation("client2", refChambre, 3, 8);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.RESERVATION_DEJA_EXISTANTE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test04Consommation01CreationSansRemise() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		//
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixProduit }, Long.class));
	}

	@Test
	public void test04Consommation02CreationAvecRemise() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final Double prixAvecRemise = 1.8;

		//
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, prixAvecRemise, 2));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixAvecRemise }, Long.class));
	}

	@Test
	public void test04Consommation03KoSansProduit() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8));
		final Produit produit = new Produit();
		produit.setReference(Entite.genererReference(Produit.class, 2L));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test04Consommation04CreationKoSansReservation() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Reservation reservation = new Reservation();
		reservation.setReference(Entite.genererReference(Reservation.class, 2L));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		//
		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test04Consommation05Rechercher() {
		//
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8));
		final Produit produit1 = new Produit();
		produit1.setReference(this.reservationService.sauvegarderProduit(new Produit("produit1", 1.99, "rouge")));
		final Produit produit2 = new Produit();
		produit2.setReference(this.reservationService.sauvegarderProduit(new Produit("produit2", 3.99, "bleu")));
		this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit1, null, 3));
		this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit2, 2.0, 2));

		//
		final Collection<Consommation> consommations = this.reservationService.rechercherConsommationsDuneReservation(reservation.getReference());

		//
		Assert.assertNotNull(consommations);
		Assert.assertEquals(2, consommations.size());
	}

	@Test
	public void test04Consommation06Suppression() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));

		//
		this.reservationService.supprimerConsommation(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test04Consommation07CreationKoReservationPasEnCours() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 7, 8));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.RESERVATION_PAS_EN_COURS));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}
}
