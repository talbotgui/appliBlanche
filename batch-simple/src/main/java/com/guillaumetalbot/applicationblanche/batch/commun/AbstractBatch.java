package com.guillaumetalbot.applicationblanche.batch.commun;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.guillaumetalbot.applicationblanche.batch.commun.listener.NotificationDebutFinDeJobListener;
import com.guillaumetalbot.applicationblanche.batch.csvclient.listener.FinCsvClientBatchListener;

public class AbstractBatch {

	protected static final String BEAN_SUFFIX_DESTINATION = "Destination";
	protected static final String BEAN_SUFFIX_PROCESSEUR = "Processeur";
	protected static final String BEAN_SUFFIX_SOURCE = "Source";

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

}
