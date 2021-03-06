package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.guillaumetalbot.applicationblanche.metier.application.SpringApplicationForTests;

@SpringBootTest(classes = SpringApplicationForTests.class)
public class LibelleServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(LibelleServiceTest.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private LibelleService libelleService;

	@BeforeEach
	public void before() throws IOException, URISyntaxException {
		LOG.info("---------------------------------------------------------");

		// Destruction des données
		final Collection<String> strings = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("db/dataPurge.sql").toURI()));
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		LOG.info("Execute SQL : {}", strings);
		jdbc.batchUpdate(strings.toArray(new String[strings.size()]));

	}

//	@Test
	@Test
	public void test01ListerLibelles() {
		//
		final String langue = "FR";
		final JdbcTemplate jdbc = new JdbcTemplate(this.dataSource);
		jdbc.update("insert into LIBELLE (clef, libelle, langue) values (?,?,?)", "clef", "libelle", langue);

		//
		final Map<String, String> libelles = this.libelleService.listerParLangue(langue);

		//
		Assertions.assertNotNull(langue);
		Assertions.assertEquals(1, libelles.size());
	}
}
