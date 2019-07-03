package com.guillaumetalbot.applicationblanche.batch.csvclient;

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
public class ClientCsvBatchIntegrationTest extends AbstractBatchIntegrationTest {

	@Test
	public void testBatchCasNominal() throws Exception {
		//
		final JobLauncherTestUtils utilitaireJob = super.creerUtilitaireJob(ClientCsvBatch.NOM_JOB);
		super.deplacerFichier("src/test/resources/data/exempleImportClient.csv", CHEMIN_IMPORT_CSV_CLIENT);

		//
		final JobExecution jobExecution = utilitaireJob.launchJob();

		//
		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assertions.assertEquals((Long) 13L, this.jdbcTemplate.queryForObject("select count(*) from CLIENT", Long.class));
		Assertions.assertEquals((Long) 13L, this.jdbcTemplate.queryForObject("select count(*) from CLIENT where nom=upper(nom)", Long.class));
		Assertions.assertEquals((Long) 13L, this.jdbcTemplate.queryForObject("select count(*) from ADRESSE", Long.class));
	}
}
