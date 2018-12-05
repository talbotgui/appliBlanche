package com.guillaumetalbot.applicationblanche.rest.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogsReguliersComposant {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(LogsReguliersComposant.class);

	@Scheduled(fixedDelay = 36001000)
	public void loggerLeBonFonctionnementToutesLesHeures() {
		LOG.info("Application vivante");
	}
}
