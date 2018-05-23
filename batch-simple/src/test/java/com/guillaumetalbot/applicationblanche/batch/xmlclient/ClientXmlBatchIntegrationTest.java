package com.guillaumetalbot.applicationblanche.batch.xmlclient;

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
import com.guillaumetalbot.applicationblanche.batch.BatchApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BatchApplicationTest.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientXmlBatchIntegrationTest extends AbstractBatchIntegrationTest {

	@Test
	public void testBatchCasNominal() throws Exception {
		//
		final JobLauncherTestUtils utilitaireJob = super.creerUtilitaireJob(ClientXmlBatch.NOM_JOB);
		super.deplacerFichier("src/test/resources/data/exempleImportClient.xml", CHEMIN_IMPORT_XML_CLIENT);

		//
		final JobExecution jobExecution = utilitaireJob.launchJob();

		//
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assert.assertEquals((Long) 12L, this.jdbcTemplate.queryForObject("select count(*) from CLIENT", Long.class));
		Assert.assertEquals((Long) 12L, this.jdbcTemplate.queryForObject("select count(*) from ADRESSE", Long.class));
	}
}
