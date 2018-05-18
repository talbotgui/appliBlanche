package com.guillaumetalbot.applicationblanche.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractBatchIntegrationTest {

	protected static final String CHEMIN_IMPORT_CSV_CLIENT = "target/test-classes/exempleImportClient.csv";
	protected static final String CHEMIN_IMPORT_JSON_CLIENT = "target/test-classes/exempleImportClient.json";
	protected static final String CHEMIN_IMPORT_XML_CLIENT = "target/test-classes/exempleImportClient.xml";

	private static final Logger LOG = LoggerFactory.getLogger(AbstractBatchIntegrationTest.class);

	@Autowired
	private ApplicationContext applicationContext;

	/** Connexion à la base de données. */
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRepository jobRepository;

	/**
	 * Objet permettant de manipuler le démarrage et l'arrêt des job
	 *
	 * @throws NoSuchJobException
	 */
	protected JobLauncherTestUtils creerUtilitaireJob(final String nomJob) throws NoSuchJobException {
		final JobLauncherTestUtils utilitaire = new JobLauncherTestUtils();
		utilitaire.setJobLauncher(this.jobLauncher);
		utilitaire.setJobRepository(this.jobRepository);
		utilitaire.setJob((Job) this.applicationContext.getBean(nomJob));
		return utilitaire;
	}

	protected void deplacerFichier(final String source, final String destination) throws IOException {

		// Validation de la source
		final File fichierSource = new File(source);
		if (!fichierSource.exists() || !fichierSource.isFile()) {
			throw new FileNotFoundException(source);
		}

		final URI cheminDuFichierDansLesSources = fichierSource.toURI();
		final URI cheminAttenduParLeBatch = new File(destination).toURI();
		Files.copy(Paths.get(cheminDuFichierDansLesSources), Paths.get(cheminAttenduParLeBatch), StandardCopyOption.REPLACE_EXISTING);
	}

	@Before
	public void nettoyerBaseDeDonnées() {
		LOG.info("************************");
		this.jdbcTemplate.batchUpdate("delete from ADRESSE", "delete from CLIENT");
	}

	@After
	public void nettoyerFichiersDeTest() throws IOException {
		Files.deleteIfExists(new File(CHEMIN_IMPORT_CSV_CLIENT).toPath());
		Files.deleteIfExists(new File(CHEMIN_IMPORT_XML_CLIENT).toPath());
		Files.deleteIfExists(new File(CHEMIN_IMPORT_JSON_CLIENT).toPath());
	}

	@Autowired
	protected void setDataSource(final DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}
}
