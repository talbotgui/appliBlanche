package com.guillaumetalbot.applicationblanche.batch.jsonclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.guillaumetalbot.applicationblanche.batch.AbstractBatchIntegrationTest;
import com.guillaumetalbot.applicationblanche.batch.BatchApplicationTest;

@SpringBootTest(classes = BatchApplicationTest.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientJsonBatchIntegrationTest extends AbstractBatchIntegrationTest {

	@Test
	public void testBatchCasNominal() throws Exception {
		//
		final JobLauncherTestUtils utilitaireJob = super.creerUtilitaireJob(ClientJsonBatch.NOM_JOB);
		super.deplacerFichier("src/test/resources/data/exempleImportClient.json", CHEMIN_IMPORT_JSON_CLIENT);

		//
		final JobExecution jobExecution = utilitaireJob.launchJob();

		//
		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assertions.assertEquals((Long) 11L, this.jdbcTemplate.queryForObject("select count(*) from CLIENT", Long.class));
		Assertions.assertEquals((Long) 11L, this.jdbcTemplate.queryForObject("select count(*) from ADRESSE", Long.class));
	}
}
