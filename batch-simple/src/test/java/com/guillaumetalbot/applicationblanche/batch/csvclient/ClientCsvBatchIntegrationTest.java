package com.guillaumetalbot.applicationblanche.batch.csvclient;

import java.io.File;

import org.assertj.core.util.Files;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guillaumetalbot.applicationblanche.batch.AbstractBatchIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientCsvBatchApplicationTest.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientCsvBatchIntegrationTest extends AbstractBatchIntegrationTest {
	private static final String CHEMIN_IMPORT_CSV_CLIENT = "target/test-classes/exempleImportCsvClient.csv";

	@AfterClass
	public static void nettoyerTest() {
		Files.delete(new File(CHEMIN_IMPORT_CSV_CLIENT));
	}

	@Test
	public void testBatchAvecUnFichierSimple() throws Exception {
		//
		final JobLauncherTestUtils utilitaireJob = super.creerUtilitaireJob(ClientCsvBatch.NOM_JOB);
		super.deplacerFichier("src/test/resources/data/exempleImportCsvClient.csv", CHEMIN_IMPORT_CSV_CLIENT);

		//
		final JobExecution jobExecution = utilitaireJob.launchJob();

		//
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assert.assertEquals((Long) 13L, this.jdbcTemplate.queryForObject("select count(*) from CLIENT", Long.class));
		Assert.assertEquals((Long) 13L, this.jdbcTemplate.queryForObject("select count(*) from ADRESSE", Long.class));
	}
}
