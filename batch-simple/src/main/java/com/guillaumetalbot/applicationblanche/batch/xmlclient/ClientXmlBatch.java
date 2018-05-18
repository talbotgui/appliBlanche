package com.guillaumetalbot.applicationblanche.batch.xmlclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.guillaumetalbot.applicationblanche.batch.commun.AbstractBatch;
import com.guillaumetalbot.applicationblanche.batch.csvclient.dto.LigneCsvImportClient;
import com.guillaumetalbot.applicationblanche.batch.csvclient.processor.LigneProcessor;

/**
 * Configuration du batch d'import des clients par un fichier XML.
 *
 * Attention, le nom des BEANs est par défaut celui de la méthode qui lui donne naissance. Pour permettre de conserver une homogénéité des
 * configurations des batchs, il est nécessaire de définir le nom de chaque BEAN dans l'annotation.
 *
 * Attention, pour permettre la résolution des paramètres des méthodes de contruction, il est nécessaire d'utiliser l'annotation @Qualifier.
 */
@Configuration
public class ClientXmlBatch extends AbstractBatch {

	public static final String NOM_JOB = "importclientxml";
	private static final String NOM_STEP_1 = NOM_JOB + "Step1";

	/**
	 * Bean permettant la lecture du CSV
	 */
	@Bean(name = NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	public ItemReader<LigneCsvImportClient> creer1Source() {

		// Parser de chaque fragment XML

		final Map<String, String> alias = new HashMap<>();
		alias.put("client", LigneCsvImportClient.class.getCanonicalName());
		alias.put("nomClient", String.class.getCanonicalName());
		alias.put("rue", String.class.getCanonicalName());
		alias.put("codePostal", String.class.getCanonicalName());
		alias.put("ville", String.class.getCanonicalName());
		final XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(alias);

		return new StaxEventItemReaderBuilder<LigneCsvImportClient>().name(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
				// Chemin d'accès
				.resource(new ClassPathResource("exempleImportClient.xml"))
				// XPath permettant de lire chaque ligne
				.addFragmentRootElements("client")
				// Pour transformer un noeud XML en objet Java
				.unmarshaller(marshaller)
				// Pas de plantage si le fichier n'est pas présent
				.strict(false)
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
				// .sql("insert into CLIENT (NOM, ID, ADRESSE_ID) values (:nomClient, next value for hibernate_sequence, next value for
				// hibernate_sequence)")
				.sql("insert into CLIENT (NOM) values (:nomClient)")
				// DS
				.dataSource(super.datasource);
		final JdbcBatchItemWriter<LigneCsvImportClient> writer1 = builder.build();
		writer1.afterPropertiesSet();

		// Builder pour la seconde requête à exécuter
		builder.sql("insert into ADRESSE (RUE, CODE_POSTAL, VILLE, ID) values (:rue, :codePostal, :ville, current value for hibernate_sequence)");
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
				// .processor(this.cprocessor())
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
