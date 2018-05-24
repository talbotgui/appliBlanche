package com.guillaumetalbot.applicationblanche.batch.clientmultidest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
public class ClientMultiDestinationBatchIntegrationTest extends AbstractBatchIntegrationTest {

	private static final List<String> CHEMINS_EXPORT_CLIENT = Arrays.asList("target/test-classes/exportClient.xml",
			"target/test-classes/exportClient.csv");
	private static final List<Long> LONGUEUR_FICHIERS_EXPORTES = Arrays.asList(1787L, 630L);

	@Test
	public void testBatchCasNominal() throws Exception {
		//
		final List<String> sqls = Files.readAllLines(Paths.get("src/test/resources/data/clients.sql"));
		super.jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
		final JobLauncherTestUtils utilitaireJob = super.creerUtilitaireJob(ClientMultiDestinationBatch.NOM_JOB);
		super.supprimerFichiers(CHEMINS_EXPORT_CLIENT);

		//
		final JobExecution jobExecution = utilitaireJob.launchJob();

		//
		Assert.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		int i = 0;
		for (final String chemin : CHEMINS_EXPORT_CLIENT) {
			final File fichier = new File(chemin);
			Assert.assertTrue("Existance du fichier " + chemin, fichier.exists());
			Assert.assertEquals(LONGUEUR_FICHIERS_EXPORTES.get(i).longValue(), fichier.length());
			i++;
		}

	}
}
