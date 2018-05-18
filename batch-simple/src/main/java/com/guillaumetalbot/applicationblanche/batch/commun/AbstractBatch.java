package com.guillaumetalbot.applicationblanche.batch.commun;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.guillaumetalbot.applicationblanche.batch.commun.listener.NotificationDebutFinDeJobListener;
import com.guillaumetalbot.applicationblanche.batch.csvclient.listener.FinCsvClientBatchListener;

public class AbstractBatch {

	/** Elements de nom des Beans Spring */
	protected static final String BEAN_SUFFIX_DESTINATION = "Destination";

	/** Elements de nom des Beans Spring */
	protected static final String BEAN_SUFFIX_PROCESSEUR = "Processeur";

	/** Elements de nom des Beans Spring */
	protected static final String BEAN_SUFFIX_SOURCE = "Source";

	/** Elements des clefs de configuration */
	protected static final String PREFIX_CONFIGURATION = "${mesBatchs.";

	/** Elements des clefs de configuration */
	protected static final String SUFFIX_CHEMIN_SOURCE = ".cheminSource}";

	/** Elements des clefs de configuration */
	protected static final String SUFFIX_ECHEC_SI_FICHIER_ABSENT = ".echecSiFichierAbsent}";

	@Autowired
	protected DataSource datasource;

	@Autowired
	protected FinCsvClientBatchListener debutFinBatchlistener;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected NotificationDebutFinDeJobListener notificationFinDeJobListener;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	/**
	 * Recherche le fichier et retour une ressource correspondante (FileSystem ou Classpath)
	 *
	 * @param chemin
	 * @return
	 */
	public Resource creerRessource(final String chemin) {
		final File fichier = new File(chemin);
		if (fichier.exists() && fichier.isFile()) {
			return new FileSystemResource(fichier);
		} else {
			return new ClassPathResource(chemin);
		}
	}

}
