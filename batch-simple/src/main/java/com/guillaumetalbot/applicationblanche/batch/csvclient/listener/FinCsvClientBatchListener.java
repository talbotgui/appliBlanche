package com.guillaumetalbot.applicationblanche.batch.csvclient.listener;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinCsvClientBatchListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(FinCsvClientBatchListener.class);

	@Autowired
	private DataSource ds;

	@Override
	public void afterJob(final JobExecution jobExecution) {
		super.afterJob(jobExecution);

		final String nomJob = jobExecution.getJobInstance().getJobName();

		final JdbcTemplate jdbc = new JdbcTemplate(this.ds);
		final Long nbLignesClientInserees = jdbc.queryForObject("select count(*) from CLIENT", Long.class);
		final Long nbLignesAdresseInserees = jdbc.queryForObject("select count(*) from ADRESSE", Long.class);

		LOG.info("Batch '{}' terminé. Nombre de lignes insérées : CLIENT={}, ADRESSE={}", nomJob, nbLignesClientInserees, nbLignesAdresseInserees);
	}

}
