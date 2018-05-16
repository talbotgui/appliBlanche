package com.guillaumetalbot.applicationblanche.batch.commun.listener;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * Composant générant un log à la fin de chaque job
 */
@Component
public class NotificationDebutFinDeJobListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationDebutFinDeJobListener.class);

	@Override
	public void afterJob(final JobExecution jobExecution) {

		final long duree = Duration.between(jobExecution.getStartTime().toInstant(), jobExecution.getEndTime().toInstant()).getSeconds();

		LOG.info("Job '{}' terminé avec le statut {} (démarrage à {}, temps d'exécution de {}s)", jobExecution.getJobInstance().getJobName(),
				jobExecution.getStatus(), jobExecution.getStartTime(), duree);
	}

	@Override
	public void beforeJob(final JobExecution jobExecution) {
		super.beforeJob(jobExecution);

		final String nomJob = jobExecution.getJobInstance().getJobName();
		LOG.info("Démarrage du batch '{}'", nomJob);
	}

}
