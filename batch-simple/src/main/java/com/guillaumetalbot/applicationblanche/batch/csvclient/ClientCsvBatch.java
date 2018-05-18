package com.guillaumetalbot.applicationblanche.batch.csvclient;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guillaumetalbot.applicationblanche.batch.commun.AbstractBatch;
import com.guillaumetalbot.applicationblanche.batch.csvclient.dto.LigneCsvImportClient;
import com.guillaumetalbot.applicationblanche.batch.csvclient.processor.LigneProcessor;

/**
 * Configuration du batch d'import des clients par un fichier CSV.
 *
 * Attention, le nom des BEANs est par défaut celui de la méthode qui lui donne naissance. Pour permettre de conserver une homogénéité des
 * configurations des batchs, il est nécessaire de définir le nom de chaque BEAN dans l'annotation.
 *
 * Attention, pour permettre la résolution des paramètres des méthodes de contruction, il est nécessaire d'utiliser l'annotation @Qualifier.
 */
@Configuration
public class ClientCsvBatch extends AbstractBatch {

	private static final String COLONNE_CODE_POSTAL = "codePostal";
	private static final String COLONNE_NOM_CLIENT = "nomClient";
	private static final String COLONNE_RUE = "rue";
	private static final String COLONNE_VILLE = "ville";
	public static final String NOM_JOB = "importclientcsv";
	private static final String NOM_STEP_1 = NOM_JOB + "Step1";

	@Value(PREFIX_CONFIGURATION + NOM_JOB + SUFFIX_CHEMIN_SOURCE)
	private String cheminSource;

	@Value(PREFIX_CONFIGURATION + NOM_JOB + SUFFIX_ECHEC_SI_FICHIER_ABSENT)
	private Boolean echecSiFichierAbsent;

	/**
	 * Bean permettant la lecture du CSV
	 */
	@Bean(name = NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	public ItemReader<LigneCsvImportClient> creer1Source() {

		// Type de fichier avec séparateur
		final DelimitedLineTokenizer dlt = new DelimitedLineTokenizer(";");
		// mapping des colonnes du CSV avec le DTO
		dlt.setNames(new String[] { COLONNE_NOM_CLIENT, COLONNE_RUE, COLONNE_CODE_POSTAL, COLONNE_VILLE });

		return new FlatFileItemReaderBuilder<LigneCsvImportClient>().name(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
				// Chemin d'accès
				.resource(super.creerRessource(this.cheminSource))
				// Comment parser chaque ligne
				.lineTokenizer(dlt)
				// Pas de plantage si le fichier n'est pas présent
				.strict(this.echecSiFichierAbsent)
				// Transformation en objet
				.fieldSetMapper(new BeanWrapperFieldSetMapper<LigneCsvImportClient>() {
					{
						this.setTargetType(LigneCsvImportClient.class);
					}
				})
				//
				.build();
	}

	@Bean(name = NOM_STEP_1 + BEAN_SUFFIX_PROCESSEUR)
	@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_PROCESSEUR)
	public LigneProcessor creer2processeur() {
		return new LigneProcessor();
	}

	@Bean(name = NOM_STEP_1 + BEAN_SUFFIX_DESTINATION)
	@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_DESTINATION)
	public ItemWriter<LigneCsvImportClient> creer3Destination() {
		// Création d'un builder
		final JdbcBatchItemWriterBuilder<LigneCsvImportClient> builder = new JdbcBatchItemWriterBuilder<LigneCsvImportClient>()
				// Les données sont issues du processeur
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				// La requête à exécuter
				.sql("insert into ADRESSE (RUE, CODE_POSTAL, VILLE, ID) values (:rue, :codePostal, :ville, next value for hibernate_sequence)")
				// DS
				.dataSource(super.datasource);
		final JdbcBatchItemWriter<LigneCsvImportClient> writer1 = builder.build();
		writer1.afterPropertiesSet();

		// Builder pour la seconde requête à exécuter
		builder.sql("insert into CLIENT (NOM, ID, ADRESSE_ID)"//
				+ " select :nomClient, next value for hibernate_sequence, a.ID"//
				+ " from ADRESSE a where a.RUE=:rue and a.CODE_POSTAL=:codePostal and a.VILLE=:ville");
		final JdbcBatchItemWriter<LigneCsvImportClient> writer2 = builder.build();
		writer2.afterPropertiesSet();

		// Renvoi d'une destination/writer composite
		return new CompositeItemWriterBuilder<LigneCsvImportClient>().delegates(Arrays.asList(writer1, writer2)).build();
	}

	@Bean(name = NOM_STEP_1)
	@Qualifier(NOM_STEP_1)
	public Step creer4ConfigurationEtape(@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE) final ItemReader<LigneCsvImportClient> source,
			@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_DESTINATION) final ItemWriter<LigneCsvImportClient> destination) {
		return this.stepBuilderFactory.get(NOM_STEP_1)
				// lecture de la source par paquet de 10
				.<LigneCsvImportClient, LigneCsvImportClient>chunk(10).reader(source)
				// destination
				.writer(destination).build();
	}

	@Bean(name = NOM_JOB)
	@Qualifier(NOM_JOB)
	public Job creer5ConfigurationJob(@Qualifier(NOM_STEP_1) final Step etape1) {
		return this.jobBuilderFactory.get(NOM_JOB)
				//
				.incrementer(new RunIdIncrementer())
				// Ajout d'un listener
				.listener(this.notificationFinDeJobListener).listener(this.debutFinBatchlistener)
				// Liste des étapes du job
				.flow(etape1)
				//
				.end().build();
	}

}
