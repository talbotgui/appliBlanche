package com.guillaumetalbot.applicationblanche.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class AbstractBatchIntegrationTest {

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
		utilitaire.setJob(this.rechercherJobDansContext(nomJob));
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

	private Job rechercherJobDansContext(final String nomJob) {
		for (final Job job : this.applicationContext.getBeansOfType(Job.class).values()) {
			if (nomJob.equals(job.getName())) {
				return job;
			}
		}
		return null;
	}

	@Autowired
	protected void setDataSource(final DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}

}
