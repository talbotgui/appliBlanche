package com.guillaumetalbot.applicationblanche.batch.clientmultidest;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.guillaumetalbot.applicationblanche.batch.commun.AbstractBatch;
import com.guillaumetalbot.applicationblanche.batch.csvclient.dto.LigneCsvImportClient;
import com.guillaumetalbot.applicationblanche.batch.csvclient.processor.LigneProcessor;

/**
 * Configuration du batch d'export des clients en CSV, XML et JSON.
 *
 * Attention, le nom des BEANs est par défaut celui de la méthode qui lui donne naissance. Pour permettre de conserver une homogénéité des
 * configurations des batchs, il est nécessaire de définir le nom de chaque BEAN dans l'annotation.
 *
 * Attention, pour permettre la résolution des paramètres des méthodes de contruction, il est nécessaire d'utiliser l'annotation @Qualifier.
 */
@Configuration
public class ClientMultiDestinationBatch extends AbstractBatch {

	private static final String ATTRIBUT_CODE_POSTAL = "codePostal";
	private static final String ATTRIBUT_NOM_CLIENT = "nomClient";
	private static final String ATTRIBUT_RUE = "rue";
	private static final String ATTRIBUT_VILLE = "ville";
	private static final String BALISE_XML_CLIENT = "client";

	public static final String NOM_JOB = "exportclientmulti";
	private static final String NOM_STEP_1 = NOM_JOB + "Step1";
	private static final String NOM_STEP_2 = NOM_JOB + "Step2";

	@Value(PREFIX_CONFIGURATION + NOM_JOB + ".csv" + SUFFIX_CHEMIN_DESTINATION)
	private String cheminDestinationCsv;

	@Value(PREFIX_CONFIGURATION + NOM_JOB + ".xml" + SUFFIX_CHEMIN_DESTINATION)
	private String cheminDestinationXml;

	@Value(PREFIX_CONFIGURATION + NOM_JOB + SUFFIX_SUPPRIME_SI_FICHIER_DESTINATION_PRESENT)
	private Boolean supprimeFichierSiExistant;

	/**
	 * Bean permettant la lecture
	 */
	@Bean(name = NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
	public ItemReader<LigneCsvImportClient> creer1Source() {
		return new JdbcCursorItemReaderBuilder<LigneCsvImportClient>().name(NOM_STEP_1 + BEAN_SUFFIX_SOURCE)
				// Source de données
				.dataSource(this.datasource)
				// Pagination
				.fetchSize(20)
				// Requête SQL
				.sql("select c.NOM as nomClient, a.RUE, a.CODE_POSTAL as codePostal, a.VILLE from CLIENT c left join ADRESSE a on c.ADRESSE_ID=a.ID order by c.ID")
				// Transformation
				.rowMapper(new BeanPropertyRowMapper<>(LigneCsvImportClient.class))
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
	public ItemWriter<LigneCsvImportClient> creer3Destination1() {
		final String[] nomsColonnesEntete = new String[] { ATTRIBUT_NOM_CLIENT, ATTRIBUT_RUE, ATTRIBUT_CODE_POSTAL, ATTRIBUT_VILLE };
		final String separateurDeChamps = ";";

		final FlatFileHeaderCallback entete = w -> w.write(String.join(separateurDeChamps, nomsColonnesEntete));

		final BeanWrapperFieldExtractor<LigneCsvImportClient> extracteurDeChamps = new BeanWrapperFieldExtractor<>();
		extracteurDeChamps.setNames(nomsColonnesEntete);

		final DelimitedLineAggregator<LigneCsvImportClient> generateurContenu = new DelimitedLineAggregator<>();
		generateurContenu.setDelimiter(separateurDeChamps);
		generateurContenu.setFieldExtractor(extracteurDeChamps);

		return new FlatFileItemWriterBuilder<LigneCsvImportClient>().name(NOM_STEP_1 + BEAN_SUFFIX_DESTINATION)
				// Traitement d'un fichier déjà existant
				.append(false).shouldDeleteIfExists(this.supprimeFichierSiExistant)
				// Paramètres du fichier de sortie
				.encoding(StandardCharsets.UTF_8.name()).lineSeparator("\n")
				// Definition de l'entete du fichier
				.headerCallback(entete)
				// Paramètres de concaténation des lignes
				.lineAggregator(generateurContenu)
				// Fichier de destination
				.resource(new FileSystemResource(new File(this.cheminDestinationCsv)))
				//
				.build();
	}

	@Bean(name = NOM_STEP_2 + BEAN_SUFFIX_DESTINATION)
	@Qualifier(NOM_STEP_2 + BEAN_SUFFIX_DESTINATION)
	public ItemWriter<LigneCsvImportClient> creer3Destination2() {

		final Map<String, String> alias = new HashMap<>();
		alias.put(BALISE_XML_CLIENT, LigneCsvImportClient.class.getCanonicalName());
		alias.put(ATTRIBUT_NOM_CLIENT, String.class.getCanonicalName());
		alias.put(ATTRIBUT_RUE, String.class.getCanonicalName());
		alias.put(ATTRIBUT_CODE_POSTAL, String.class.getCanonicalName());
		alias.put(ATTRIBUT_VILLE, String.class.getCanonicalName());
		final XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(alias);

		return new StaxEventItemWriterBuilder<LigneCsvImportClient>().name(NOM_STEP_2 + BEAN_SUFFIX_DESTINATION)
				// Paramètres du fichier de sortie
				.encoding(StandardCharsets.UTF_8.name()).overwriteOutput(this.supprimeFichierSiExistant)
				// Fichier de destination
				.resource(new FileSystemResource(new File(this.cheminDestinationXml)))
				// Paramètres du formation XML
				.marshaller(marshaller).rootTagName("clients")
				//
				.build();
	}

	@Bean(name = NOM_STEP_1)
	@Qualifier(NOM_STEP_1)
	public Step creer4ConfigurationEtape1(@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE) final ItemReader<LigneCsvImportClient> source,
			@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_DESTINATION) final ItemWriter<LigneCsvImportClient> destination) {
		return this.stepBuilderFactory.get(NOM_STEP_1)
				// lecture de la source par paquet de 10
				.<LigneCsvImportClient, LigneCsvImportClient>chunk(10).reader(source)
				// destination
				.writer(destination).build();
	}

	@Bean(name = NOM_STEP_2)
	@Qualifier(NOM_STEP_2)
	public Step creer4ConfigurationEtape2(@Qualifier(NOM_STEP_1 + BEAN_SUFFIX_SOURCE) final ItemReader<LigneCsvImportClient> source,
			@Qualifier(NOM_STEP_2 + BEAN_SUFFIX_DESTINATION) final ItemWriter<LigneCsvImportClient> destination) {
		return this.stepBuilderFactory.get(NOM_STEP_2)
				// lecture de la source par paquet de 10
				.<LigneCsvImportClient, LigneCsvImportClient>chunk(1).reader(source)
				// destination
				.writer(destination).build();
	}

	@Bean(name = NOM_JOB)
	@Qualifier(NOM_JOB)
	public Job creer5ConfigurationJob(@Qualifier(NOM_STEP_1) final Step etape1, @Qualifier(NOM_STEP_2) final Step etape2) {
		return this.jobBuilderFactory.get(NOM_JOB)
				//
				.incrementer(new RunIdIncrementer())
				// Ajout d'un listener
				.listener(this.notificationFinDeJobListener).listener(this.debutFinBatchlistener)
				// Liste des étapes du job
				.flow(etape1).next(etape2)
				//
				.end().build();
	}

}
