package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;
import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ClientServiceTest.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private DataSource dataSource;

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
	public void test01Client01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		Assert.assertNotNull(refClient);
		Assert.assertEquals(Entite.genererReference(Client.class, 1L), refClient);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CLIENT", Long.class));
	}

	@Test
	public void test01Client02tModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nouveauNom = "nomClient2";

		//
		this.clientService.sauvegarderClient(refClient, nouveauNom);

		//
		Assert.assertNotNull(refClient);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CLIENT", Long.class));
		Assert.assertEquals(nouveauNom, jdbc.queryForObject("select NOM from CLIENT", String.class));
	}

	@Test
	public void test01Client03SauvegardetNonExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = Entite.genererReference(Client.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderClient(refClient, "nom");
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from CLIENT", Long.class));
	}

	@Test
	public void test02Adresse01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		final String refAdresse = this.clientService.sauvegarderAdresse(refClient, new Adresse("rue", "codePostal", "ville"));

		//
		Assert.assertNotNull(refAdresse);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

	@Test
	public void test02Adresse02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		String refAdresse = this.clientService.sauvegarderAdresse(refClient, new Adresse("rue", "codePostal", "ville"));
		final String nouvelleRue = "rue2";
		//
		refAdresse = this.clientService.sauvegarderAdresse(refClient, new Adresse(refAdresse, nouvelleRue, "codePostal2", "ville2"));

		//
		Assert.assertNotNull(refAdresse);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
		Assert.assertEquals(nouvelleRue, jdbc.queryForObject("select RUE from ADRESSE", String.class));
	}

	@Test
	public void test02Adresse03ClientInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refAdresse = Entite.genererReference(Adresse.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(refAdresse, new Adresse("rue", "codePostal", "ville"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

	@Test
	public void test02Adresse04ClientNonLie() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refClient2 = this.clientService.sauvegarderClient(null, "nomClient2");
		final String refAdresse = this.clientService.sauvegarderAdresse(refClient, new Adresse("rue", "codePostal", "ville"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(refClient2, new Adresse(refAdresse, "rue2", "codePostal2", "ville2"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

	@Test
	public void test02Adresse05AdresseInexistante() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refAdresse = Entite.genererReference(Adresse.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(refClient, new Adresse(refAdresse, "rue2", "codePostal2", "ville2"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

	@Test
	public void test03Dossier01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nomDossier = "nomDossier";

		//
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier(nomDossier));

		//
		Assert.assertNotNull(refDossier);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DOSSIER where NOM=? and DATE_CREATION is not null",
				new Object[] { nomDossier }, Long.class));
	}

	@Test
	public void test03Dossier02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nomDossier = "nomDossier";
		String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier(nomDossier));
		final String nomDossier2 = "nomDossier2";

		//
		refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier(refDossier, nomDossier2));

		//
		Assert.assertNotNull(refDossier);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DOSSIER", Long.class));
		Assert.assertEquals(nomDossier2, jdbc.queryForObject("select NOM from DOSSIER", String.class));
	}

	@Test
	public void test03Dossier03ClientInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refDossier = Entite.genererReference(Dossier.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(refDossier, new Dossier("nomDossier"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from Dossier", Long.class));
	}

	@Test
	public void test03Dossier04ClientNonLie() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refClient2 = this.clientService.sauvegarderClient(null, "nomClient2");
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(refClient2, new Dossier(refDossier, "nomDossier2"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DOSSIER", Long.class));
	}

	@Test
	public void test03Dossier05DossierInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refDossier = Entite.genererReference(Dossier.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(refClient, new Dossier(refDossier, "nomDossier2"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from DOSSIER", Long.class));
	}

	@Test
	public void test04Demande01CreationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier"));
		final String description = "maDemande";

		//
		final String refDemande = this.clientService.sauvegarderDemande(refDossier, new Demande(description, description));

		//
		Assert.assertNotNull(refDemande);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from DEMANDE where DESCRIPTION_COURTE=?", new Object[] { description }, Long.class));
	}

	@Test
	public void test04Demande02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier"));
		final String description = "maDemande";
		final String description2 = "maDemande2";
		String refDemande = this.clientService.sauvegarderDemande(refDossier, new Demande(description, description));

		//
		refDemande = this.clientService.sauvegarderDemande(refDossier, new Demande(refDemande, description2, description2));

		//
		Assert.assertNotNull(refDemande);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
		Assert.assertEquals(description2, jdbc.queryForObject("select DESCRIPTION_COURTE from DEMANDE", String.class));
	}

	@Test
	public void test04Demande03DossierInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refDemande = Entite.genererReference(Demande.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(refDemande, new Demande("maDemande", "maDemande"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
	}

	@Test
	public void test04Demande04DossierNonLie() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier"));
		final String refDossier2 = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier2"));
		final String refDemande = this.clientService.sauvegarderDemande(refDossier, new Demande("maDemande", "maDemande"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(refDossier2, new Demande(refDemande, "maDemande", "maDemande"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
	}

	@Test
	public void test04Demande05DemandeInexistante() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final String refClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String refDossier = this.clientService.sauvegarderDossier(refClient, new Dossier("nomDossier"));
		final String refDemande = Entite.genererReference(Demande.class, 1L);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(refDossier, new Demande(refDemande, "maDescription", "maDescription"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
	}

}
