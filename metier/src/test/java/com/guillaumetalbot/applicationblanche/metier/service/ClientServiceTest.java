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
import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		Assert.assertNotNull(idClient);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CLIENT", Long.class));
	}

	@Test
	public void test01Client02tModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nouveauNom = "nomClient2";

		//
		this.clientService.sauvegarderClient(idClient, nouveauNom);

		//
		Assert.assertNotNull(idClient);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from CLIENT", Long.class));
		Assert.assertEquals(nouveauNom, jdbc.queryForObject("select NOM from CLIENT", String.class));
	}

	@Test
	public void test01Client03SauvegardetNonExistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderClient(1L, "nom");
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		final Long idAdresse = this.clientService.sauvegarderAdresse(idClient, new Adresse("rue", "codePostal", "ville"));

		//
		Assert.assertNotNull(idAdresse);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

	@Test
	public void test02Adresse02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		Long idAdresse = this.clientService.sauvegarderAdresse(idClient, new Adresse("rue", "codePostal", "ville"));
		final String nouvelleRue = "rue2";
		//
		idAdresse = this.clientService.sauvegarderAdresse(idClient, new Adresse(idAdresse, nouvelleRue, "codePostal2", "ville2"));

		//
		Assert.assertNotNull(idAdresse);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
		Assert.assertEquals(nouvelleRue, jdbc.queryForObject("select RUE from ADRESSE", String.class));
	}

	@Test
	public void test02Adresse03ClientInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(1L, new Adresse("rue", "codePostal", "ville"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idClient2 = this.clientService.sauvegarderClient(null, "nomClient2");
		final Long idAdresse = this.clientService.sauvegarderAdresse(idClient, new Adresse("rue", "codePostal", "ville"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(idClient2, new Adresse(idAdresse, "rue2", "codePostal2", "ville2"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(idClient, new Adresse(1L, "rue2", "codePostal2", "ville2"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nomDossier = "nomDossier";

		//
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier(nomDossier));

		//
		Assert.assertNotNull(idDossier);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DOSSIER where NOM=? and DATE_CREATION is not null",
				new Object[] { nomDossier }, Long.class));
	}

	@Test
	public void test03Dossier02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final String nomDossier = "nomDossier";
		Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier(nomDossier));
		final String nomDossier2 = "nomDossier2";

		//
		idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier(idDossier, nomDossier2));

		//
		Assert.assertNotNull(idDossier);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DOSSIER", Long.class));
		Assert.assertEquals(nomDossier2, jdbc.queryForObject("select NOM from DOSSIER", String.class));
	}

	@Test
	public void test03Dossier03ClientInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(1L, new Dossier("nomDossier"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idClient2 = this.clientService.sauvegarderClient(null, "nomClient2");
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(idClient2, new Dossier(idDossier, "nomDossier2"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDossier(idClient, new Dossier(1L, "nomDossier2"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier"));
		final String description = "maDemande";

		//
		final Long idDemande = this.clientService.sauvegarderDemande(idDossier, new Demande(description, description));

		//
		Assert.assertNotNull(idDemande);
		Assert.assertEquals((Long) 1L,
				jdbc.queryForObject("select count(*) from DEMANDE where DESCRIPTION_COURTE=?", new Object[] { description }, Long.class));
	}

	@Test
	public void test04Demande02ModificationOk() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier"));
		final String description = "maDemande";
		final String description2 = "maDemande2";
		Long idDemande = this.clientService.sauvegarderDemande(idDossier, new Demande(description, description));

		//
		idDemande = this.clientService.sauvegarderDemande(idDossier, new Demande(idDemande, description2, description2));

		//
		Assert.assertNotNull(idDemande);
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
		Assert.assertEquals(description2, jdbc.queryForObject("select DESCRIPTION_COURTE from DEMANDE", String.class));
	}

	@Test
	public void test04Demande03DossierInexistant() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(1L, new Demande("maDemande", "maDemande"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier"));
		final Long idDossier2 = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier2"));
		final Long idDemande = this.clientService.sauvegarderDemande(idDossier, new Demande("maDemande", "maDemande"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(idDossier2, new Demande(idDemande, "maDemande", "maDemande"));
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
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idDossier = this.clientService.sauvegarderDossier(idClient, new Dossier("nomDossier"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderDemande(idDossier, new Demande(1L, "maDescription", "maDescription"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 0L, jdbc.queryForObject("select count(*) from DEMANDE", Long.class));
	}

}
