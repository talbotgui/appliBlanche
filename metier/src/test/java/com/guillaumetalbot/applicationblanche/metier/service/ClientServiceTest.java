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
	public void test02Adresse03ClientNonLie() {
		//
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		final Long idClient = this.clientService.sauvegarderClient(null, "nomClient");
		final Long idAdresse = this.clientService.sauvegarderAdresse(idClient, new Adresse("rue", "codePostal", "ville"));

		//
		final Throwable thrown = Assertions.catchThrowable(() -> {
			this.clientService.sauvegarderAdresse(idClient, new Adresse(idAdresse + 1, "rue2", "codePostal2", "ville2"));
		});

		//
		Assert.assertNotNull(thrown);
		Assert.assertTrue(BusinessException.equals((Exception) thrown, BusinessException.OBJET_NON_EXISTANT));
		Assert.assertEquals((Long) 1L, jdbc.queryForObject("select count(*) from ADRESSE", Long.class));
	}

}
