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
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.EtatReservation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationParametresServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ReservationParametresServiceTest.class);

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

	private String sauvegarderUneReservation(final String client, final String refChambre, final int deltaDateDebut, final int deltaDateFin,
			final String refFormule) {
		return this.sauvegarderUneReservation(client, refChambre, deltaDateDebut, deltaDateFin, refFormule, null);
	}

	private String sauvegarderUneReservation(final String client, final String refChambre, final int deltaDateDebut, final int deltaDateFin,
			final String refFormule, final String refOption) {
		// Gestion des dates
		final LocalDate dateDebut = LocalDate.now().plus(deltaDateDebut, ChronoUnit.DAYS);
		final LocalDate dateFin = LocalDate.now().plus(deltaDateFin, ChronoUnit.DAYS);

		// Création des objets liés obligatoires
		final Chambre c = new Chambre();
		c.setReference(refChambre);
		final Formule formule = new Formule();
		formule.setReference(refFormule);

		// Création de la réservation
		final Reservation reservation = new Reservation(client, c, dateDebut, dateFin, formule);

		// Création des objets liés optionnels
		if (refOption != null) {
			final Option option = new Option();
			option.setReference(refOption);
			reservation.getOptions().add(option);
		}

		// Sauvegarde
		return this.reservationService.sauvegarderReservation(reservation);
	}

	@Test
	public void test01Produit01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test01Produit02CreationKoProduitEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));
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
		this.reservationParametresService.sauvegarderProduit(new Produit(nomDesProduits.get(2), 0.99, "rouge"));
		this.reservationParametresService.sauvegarderProduit(new Produit(nomDesProduits.get(0), 1.0, "bleu"));
		this.reservationParametresService.sauvegarderProduit(new Produit(nomDesProduits.get(1), 2.5, "rouge"));

		//
		final Collection<Produit> produits = this.reservationParametresService.listerProduits();

		//
		Assert.assertNotNull(produits);
		Assertions.assertThat(produits.stream().map((p) -> p.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesProduits);
	}

	@Test
	public void test01Produit03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String ref = this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));

		//
		this.reservationParametresService.supprimerProduit(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test01Produit04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refProduit = this.reservationParametresService.sauvegarderProduit(new Produit("nom", 1.2, "rouge"));
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		final String refFormule = this.reservationParametresService.sauvegarderFormule(new Formule("nom1", 2.6));
		final String refResa = this.sauvegarderUneReservation("client", refChambre, -1, 2, refFormule);
		this.reservationService.changeEtatReservation(refResa, EtatReservation.EN_COURS);

		final Reservation resa = new Reservation();
		resa.setReference(refResa);
		final Produit produit = new Produit();
		produit.setReference(refProduit);
		this.reservationService.sauvegarderConsommation(new Consommation(resa, produit, 2.0, 1));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.supprimerProduit(refProduit);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from PRODUIT", Long.class));
	}

	@Test
	public void test02Chambre01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test02Chambre02CreationKoChambreEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.sauvegarderChambre(new Chambre("nom1"));
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
		this.reservationParametresService.sauvegarderChambre(new Chambre(nomDesChambres.get(2)));
		this.reservationParametresService.sauvegarderChambre(new Chambre(nomDesChambres.get(0)));
		this.reservationParametresService.sauvegarderChambre(new Chambre(nomDesChambres.get(1)));

		//
		final Collection<Chambre> chambres = this.reservationParametresService.listerChambres();

		//
		Assert.assertNotNull(chambres);
		Assertions.assertThat(chambres.stream().map((c) -> c.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesChambres);
	}

	@Test
	public void test02Chambre03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));

		//
		this.reservationParametresService.supprimerChambre(refChambre);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test02Chambre04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		final String refFormule = this.reservationParametresService.sauvegarderFormule(new Formule("nom1", 2.6));
		this.sauvegarderUneReservation("client", refChambre, -1, 2, refFormule);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.supprimerChambre(refChambre);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CHAMBRE", Long.class));
	}

	@Test
	public void test03Formule01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationParametresService.sauvegarderFormule(new Formule("nom", 2.5));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from FORMULE", Long.class));
	}

	@Test
	public void test03Formule02CreationKoProduitEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderFormule(new Formule("nomF", 2.5));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.sauvegarderFormule(new Formule("nomF", 2.6));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from FORMULE", Long.class));
	}

	@Test
	public void test03Formule03Lister() {
		//
		final List<String> nomDesFormules = Arrays.asList("nom1", "nom2", "nom3");
		this.reservationParametresService.sauvegarderFormule(new Formule(nomDesFormules.get(2), 2.6));
		this.reservationParametresService.sauvegarderFormule(new Formule(nomDesFormules.get(1), 2.6));
		this.reservationParametresService.sauvegarderFormule(new Formule(nomDesFormules.get(0), 2.6));

		//
		final Collection<Formule> formules = this.reservationParametresService.listerFormules();

		//
		Assert.assertNotNull(formules);
		Assertions.assertThat(formules.stream().map((p) -> p.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesFormules);
	}

	@Test
	public void test03Formule03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String ref = this.reservationParametresService.sauvegarderFormule(new Formule("nom1", 2.6));

		//
		this.reservationParametresService.supprimerFormule(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from FORMULE", Long.class));
	}

	@Test
	public void test03Formule04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refFormule = this.reservationParametresService.sauvegarderFormule(new Formule("nom1", 2.6));
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		this.sauvegarderUneReservation("client", refChambre, -1, 2, refFormule);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.supprimerFormule(refFormule);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from FORMULE", Long.class));
	}

	@Test
	public void test04Option01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationParametresService.sauvegarderOption(new Option("nom", 2.5, false, false));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from OPTION_RESERVATION", Long.class));
	}

	@Test
	public void test04Option02CreationKoNomEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		this.reservationParametresService.sauvegarderOption(new Option("nomO", 2.5, false, false));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.sauvegarderOption(new Option("nomO", 2.6, true, true));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from OPTION_RESERVATION", Long.class));
	}

	@Test
	public void test04Option03Lister() {
		//
		final List<String> nomDesOptions = Arrays.asList("nom1", "nom2", "nom3");
		this.reservationParametresService.sauvegarderOption(new Option(nomDesOptions.get(2), 2.6, false, false));
		this.reservationParametresService.sauvegarderOption(new Option(nomDesOptions.get(1), 2.6, false, false));
		this.reservationParametresService.sauvegarderOption(new Option(nomDesOptions.get(0), 2.6, false, false));

		//
		final Collection<Option> options = this.reservationParametresService.listerOptions();

		//
		Assert.assertNotNull(options);
		Assertions.assertThat(options.stream().map((o) -> o.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesOptions);
	}

	@Test
	public void test04Option03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String ref = this.reservationParametresService.sauvegarderOption(new Option("nom1", 2.6, false, false));

		//
		this.reservationParametresService.supprimerOption(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from OPTION_RESERVATION", Long.class));
	}

	@Test
	public void test04Option04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refFormule = this.reservationParametresService.sauvegarderFormule(new Formule("nomF1", 2.1));
		final String refOption = this.reservationParametresService.sauvegarderOption(new Option("nom1", 2.6, false, false));
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		this.sauvegarderUneReservation("client", refChambre, -1, 2, refFormule, refOption);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.supprimerOption(refOption);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from OPTION_RESERVATION", Long.class));
	}

	@Test
	public void test05MoyenDePaiement01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String ref = this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nomMdp", 2.5));

		//
		Assert.assertNotNull(ref);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from MOYEN_DE_PAIEMENT", Long.class));
	}

	@Test
	public void test05MoyenDePaiement02CreationKoNomEnDouble() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String nom = "nom";
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement(nom, 2.5));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement(nom, 2.6));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_FONCTIONNELEMENT_EN_DOUBLE));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from MOYEN_DE_PAIEMENT", Long.class));
	}

	@Test
	public void test05MoyenDePaiement03Lister() {
		//
		final List<String> nomDesFormules = Arrays.asList("nom1", "nom2", "nom3");
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement(nomDesFormules.get(2), 2.6));
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement(nomDesFormules.get(1), 2.6));
		this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement(nomDesFormules.get(0), 2.6));

		//
		final Collection<MoyenDePaiement> moyens = this.reservationParametresService.listerMoyensDePaiement();

		//
		Assert.assertNotNull(moyens);
		Assertions.assertThat(moyens.stream().map((p) -> p.getNom()).collect(Collectors.toList())).containsExactlyElementsOf(nomDesFormules);
	}

	@Test
	public void test05MoyenDePaiement03SuppressionOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String ref = this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nom1", 2.6));

		//
		this.reservationParametresService.supprimerMoyenDePaiement(ref);

		//
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from MOYEN_DE_PAIEMENT", Long.class));
	}

	@Test
	public void test05MoyenDePaiement04SuppressionKo() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refChambre = this.reservationParametresService.sauvegarderChambre(new Chambre("nomC"));
		final String refFormule = this.reservationParametresService.sauvegarderFormule(new Formule("nom1", 2.6));
		final String refMoyen = this.reservationParametresService.sauvegarderMoyenDePaiement(new MoyenDePaiement("nom1", 2.6));
		final String refReservation = this.sauvegarderUneReservation("client", refChambre, -1, 2, refFormule);
		final MoyenDePaiement mdp = new MoyenDePaiement();
		mdp.setReference(refMoyen);
		final Reservation reservation = new Reservation();
		reservation.setReference(refReservation);
		this.reservationService.sauvegarderPaiement(new Paiement(LocalDate.now(), 1.0, mdp, reservation));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.reservationParametresService.supprimerMoyenDePaiement(refMoyen);
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.SUPPRESSION_IMPOSSIBLE_OBJETS_LIES));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from MOYEN_DE_PAIEMENT", Long.class));
	}

}
