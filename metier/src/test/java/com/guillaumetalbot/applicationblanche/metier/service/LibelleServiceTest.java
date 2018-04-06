package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApplicationForTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LibelleServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(LibelleServiceTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LibelleService libelleService;

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
	public void test01ListerLibelles() {
		//
		final String langue = "FR";
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		jdbc.update("insert into LIBELLE (id, clef, libelle, langue) values (?,?,?,?)", 1L, "clef", "libelle", langue);

		//
		final Map<String, String> libelles = this.libelleService.listerParLangue(langue);

		//
		Assert.assertNotNull(langue);
		Assert.assertEquals(1, libelles.size());
	}
}
