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
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
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

	private String sauvegarderUneReservation(final String client, final String refChambre, final int deltaDateDebut, final int deltaDateFin) {
		return this.sauvegarderUneReservation(client, refChambre, deltaDateDebut, deltaDateFin, false);
	}

	private String sauvegarderUneReservation(final String client, final String refChambre, final int deltaDateDebut, final int deltaDateFin,
			final boolean statutEnCours) {
		final Chambre c = new Chambre();
		c.setReference(refChambre);
		final Formule f = new Formule();
		f.setReference(this.reservationParametresService.sauvegarderFormule(new Formule(client + refChambre, 2.0)));
		final LocalDate dateDebut = LocalDate.now().plus(deltaDateDebut, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(deltaDateFin, ChronoUnit.DAYS);
		final String reference = this.reservationService.sauvegarderReservation(new Reservation(client, c, dateDebut, dateFin, f));
		if (statutEnCours) {
			this.reservationService.changeEtatReservation(reference, EtatReservation.EN_COURS);
		}
		return reference;
	}

	@Test
	public void test01Reservation01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String client = "client ";

		//
		final String ref = this.sauvegarderUneReservation(client, refChambre, -1, 2, false);

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from RESERVATION where client=?", new Object[] { client.trim() }, Long.class));
	}

	@Test
	public void test01Reservation02CreationKoChambreInexistante() {
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
	public void test01Reservation02CreationKoDatesIncoherentes() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = Entite.genererReference(Chambre.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.sauvegarderUneReservation("client", refChambre, -1, -1);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.RESERVATION_DATES_INCOHERENTES));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test01Reservation03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String ref = this.sauvegarderUneReservation("client", refChambre, -1, 2);

		//
		this.reservationService.supprimerReservation(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from RESERVATION", Long.class));
	}

	@Test
	public void test01Reservation04RechercherVide() {
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
	public void test01Reservation04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refProduit = this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		final String refResa = this.sauvegarderUneReservation("client", refChambre, -1, 2, true);
		final Reservation resa = new Reservation();
		resa.setReference(refResa);
		final Produit produit = new Produit();
		produit.setReference(refProduit);
		this.reservationService.sauvegarderConsommation(new Consommation(resa, produit, 2.0, 1));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.supprimerReservation(refResa);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from RESERVATION", Long.class));
	}

	@Test
	public void test01Reservation05RechercherTouteDedans() {
		//
		final LocalDate dateDebut = LocalDate.now().plus(-30, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(+30, ChronoUnit.DAYS);
		final String refChambre1 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom2"));
		final String refChambre3 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom3"));
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
	public void test01Reservation06RechercherAvecClassesEquivalence() {
		//
		final LocalDate dateDebut = LocalDate.now().plus(-30, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(+30, ChronoUnit.DAYS);
		final String refChambre1 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom2"));
		final String refChambre3 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom3"));
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
	public void test01Reservation07CreationKoDatesDejaPrises() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
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
	public void test01Reservation08ChangerEtatOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, 2, 4);

		//
		this.reservationService.changeEtatReservation(refReservation, EtatReservation.EN_COURS);

		//
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from RESERVATION where etat_Courant=?",
				new Object[] { EtatReservation.EN_COURS.getNumero() }, Long.class));
	}

	@Test
	public void test01Reservation09ChangerEtatKoIncoherent() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, 2, 4);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.changeEtatReservation(refReservation, EtatReservation.TERMINEE);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.TRANSITION_ETAT_IMPOSSIBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from RESERVATION where etat_courant=?",
				new Object[] { EtatReservation.ENREGISTREE.getNumero() }, Long.class));
	}

	@Test
	public void test01Reservation10RechercherParEtat1() {
		//
		final String refChambre1 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom2"));
		final String refResa1Annulee = this.sauvegarderUneReservation("client1", refChambre1, -10, -8);
		final String refResa2EnCours = this.sauvegarderUneReservation("client2", refChambre1, -8, -6);
		this.sauvegarderUneReservation("client3", refChambre2, 0, 2);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.EN_COURS);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.ANNULEE);
		this.reservationService.changeEtatReservation(refResa2EnCours, EtatReservation.EN_COURS);

		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(EtatReservation.EN_COURS, false);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(1, reservations.size());
		Assert.assertEquals(refResa2EnCours, reservations.iterator().next().getReference());
	}

	@Test
	public void test01Reservation11RechercherParEtat2() {
		//
		final String refChambre1 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom2"));
		final String refResa1Annulee = this.sauvegarderUneReservation("client1", refChambre1, -10, -8);
		final String refResa2EnCours = this.sauvegarderUneReservation("client2", refChambre1, -8, -6);
		this.sauvegarderUneReservation("client3", refChambre2, 0, 2);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.EN_COURS);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.ANNULEE);
		this.reservationService.changeEtatReservation(refResa2EnCours, EtatReservation.EN_COURS);

		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(EtatReservation.EN_COURS, true);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(1, reservations.size());
		Assert.assertEquals(refResa2EnCours, reservations.iterator().next().getReference());
	}

	@Test
	public void test01Reservation11RechercherParEtat3() {
		//
		final String refChambre1 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refChambre2 = this.reservationParametresService.sauvegarderChambre(new Chambre("nom2"));
		final String refResa1Annulee = this.sauvegarderUneReservation("client1", refChambre1, -10, -8);
		final String refResa2EnCours = this.sauvegarderUneReservation("client2", refChambre1, -8, -6);
		this.sauvegarderUneReservation("client3", refChambre2, 0, 2);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.EN_COURS);
		this.reservationService.changeEtatReservation(refResa1Annulee, EtatReservation.ANNULEE);
		this.reservationService.changeEtatReservation(refResa2EnCours, EtatReservation.EN_COURS);
		this.reservationService.changeEtatReservation(refResa2EnCours, EtatReservation.FACTUREE);

		//
		final Collection<Reservation> reservations = this.reservationService.rechercherReservations(EtatReservation.FACTUREE, true);

		//
		Assert.assertNotNull(reservations);
		Assert.assertEquals(1, reservations.size());
		Assert.assertEquals(refResa2EnCours, reservations.iterator().next().getReference());
	}

	@Test
	public void test01Reservation12MontantTotal() {
		//
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
		final String refResa = this.sauvegarderUneReservation("client2", refChambre, -8, -6);
		this.reservationService.changeEtatReservation(refResa, EtatReservation.EN_COURS);

		final Reservation reservation = new Reservation();
		reservation.setReference(refResa);
		final Produit produit = new Produit();
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", 2.1, "rouge")));
		this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));

		//
		final Double montantTotal = this.reservationService.calculerMontantTotalDuneReservation(refResa);

		//
		Assert.assertNotNull(montantTotal);
		Assert.assertEquals((Double) 8.2, montantTotal);
	}

	@Test
	public void test02Consommation01CreationSansRemise() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		//
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixProduit }, Long.class));
	}

	@Test
	public void test02Consommation02CreationAvecRemise() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final Double prixAvecRemise = 1.8;

		//
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, prixAvecRemise, 2));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixAvecRemise }, Long.class));
	}

	@Test
	public void test02Consommation03KoSansProduit() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
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
	public void test02Consommation04CreationKoSansReservation() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Reservation reservation = new Reservation();
		reservation.setReference(Entite.genererReference(Reservation.class, 2L));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

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
	public void test02Consommation05Rechercher() {
		//
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit1 = new Produit();
		produit1.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit1", 1.99, "rouge")));
		final Produit produit2 = new Produit();
		produit2.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit2", 3.99, "bleu")));
		this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit1, null, 3));
		this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit2, 2.0, 2));

		//
		final Collection<Consommation> consommations = this.reservationService.rechercherConsommationsDuneReservation(reservation.getReference());

		//
		Assert.assertNotNull(consommations);
		Assert.assertEquals(2, consommations.size());
	}

	@Test
	public void test02Consommation06SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));

		//
		this.reservationService.supprimerConsommation(reservation.getReference(), ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test02Consommation07CreationKoReservationPasEnCours() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 7, 8));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.RESERVATION_PAS_EN_COURS));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test02Consommation07SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final String ref = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 2));
		final String mauvaiseReferenceResa = Entite.genererReference(Reservation.class, 99L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.supprimerConsommation(mauvaiseReferenceResa, ref);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test02Consommation08CreationEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));

		final String ref1 = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 1));

		//
		final String ref2 = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 1));

		//
		Assert.assertEquals(ref1, ref2);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixProduit }, Long.class));
		Assert.assertEquals((Long) 2L, jdbc.queryForObject("select quantite from CONSOMMATION", new Object[] {}, Long.class));
	}

	@Test
	public void test02Consommation09ModificationQuantite() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final String refConso = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 4));

		//
		this.reservationService.modifierQuantiteConsommation(reservation.getReference(), refConso, -1);

		//
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from CONSOMMATION where prix_paye=?", new Object[] { prixProduit }, Long.class));
		Assert.assertEquals((Long) 3L, jdbc.queryForObject("select quantite from CONSOMMATION", new Object[] {}, Long.class));
	}

	@Test
	public void test02Consommation10ModificationQuantitePourSupprimer() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nom"));
		final Reservation reservation = new Reservation();
		reservation.setReference(this.sauvegarderUneReservation("client", refChambre, 0, 8, true));
		final Produit produit = new Produit();
		final Double prixProduit = 2.00;
		produit.setReference(this.reservationParametresService.sauvegarderProduit(new Produit("produit", prixProduit, "rouge")));
		final String refConso = this.reservationService.sauvegarderConsommation(new Consommation(reservation, produit, null, 4));

		//
		this.reservationService.modifierQuantiteConsommation(reservation.getReference(), refConso, -3);
		this.reservationService.modifierQuantiteConsommation(reservation.getReference(), refConso, -1);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CONSOMMATION", Long.class));
	}

	@Test
	public void test03Paiement01CreationPremier() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);

		//
		final Paiement paiement = new Paiement(LocalDate.now(), 102.2, mdp, reservation);
		final String refPaiement = this.reservationService.sauvegarderPaiement(paiement);

		//
		Assert.assertNotNull(refPaiement);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

	@Test
	public void test03Paiement02CreationComplement() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);
		final Paiement paiement = new Paiement(LocalDate.now(), null, mdp, reservation);
		this.reservationService.sauvegarderPaiement(paiement);

		//
		final Paiement paiement2 = new Paiement(LocalDate.now(), 19.1, mdp, reservation);
		final String refPaiement = this.reservationService.sauvegarderPaiement(paiement2);

		//
		Assert.assertNotNull(refPaiement);
		Assert.assertEquals((Long) 2L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

	@Test
	public void test03Paiement03Suppression() {
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);
		final String refP1 = this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 102.2, mdp, reservation));
		final String refP2 = this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 19.1, mdp, reservation));

		//
		this.reservationService.supprimerPaiement(refReservation, refP1);
		this.reservationService.supprimerPaiement(refReservation, refP2);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

	@Test
	public void test03Paiement04Rechercher() {
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 102.2, mdp, reservation));
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 19.1, mdp, reservation));

		//
		final Collection<Paiement> paiements = this.reservationService.rechercherPaiementsDuneReservation(refReservation);

		//
		Assert.assertEquals(2, paiements.size());
	}

	@Test
	public void test03Paiement05SuppressionKo() {
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);
		final String refP1 = this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 19.1, mdp, reservation));

		final String mauvaiseReservation = Entite.genererReference(Reservation.class, 999L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.supprimerPaiement(mauvaiseReservation, refP1);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

	@Test
	public void test03Paiement06CreationKoMdp() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final MoyenDePaiement mdp = new MoyenDePaiement("nomMdp", 2.5);
		mdp.setReference(Entite.genererReference(MoyenDePaiement.class, 999L));
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);

		//
		final Paiement paiement = new Paiement(LocalDate.now(), 102.2, mdp, reservation);

		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderPaiement(paiement);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

	@Test
	public void test03Paiement07CreationKoMontant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", null));
		final MoyenDePaiement mdp = this.reservationParametresService.listerMoyensDePaiement().iterator().next();
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("C1"));
		final String refReservation = this.sauvegarderUneReservation("client1", refChambre, -1, 3);
		final Reservation reservation = this.reservationService.chargerReservation(refReservation);

		//
		final Paiement paiement = new Paiement(LocalDate.now(), null, mdp, reservation);

		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationService.sauvegarderPaiement(paiement);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.ERREUR_AUCUN_MONTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from PAIEMENT", Long.class));
	}

}
