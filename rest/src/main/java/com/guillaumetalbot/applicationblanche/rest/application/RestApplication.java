package com.guillaumetalbot.applicationblanche.rest.application;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Une configuration Spring-boot pour l'application. Cette classe remplace le traditionnel fichier XML.
 *
 * Cette classe ne doit pas être utilisée en mode développement car la dépendance à la base de données n'est pas définie dans le profil par défaut.
 *
 * Il faut utiliser la classe RestApplicationForTests pour démarrer l'application en mode développement.
 */
@SpringBootApplication
@EnableSwagger2
@EntityScan(RestApplication.PACKAGE_METIER_ENTITE)
@ComponentScan({ RestApplication.PACKAGE_REST_ERREUR, RestApplication.PACKAGE_REST_SECURITE, RestApplication.PACKAGE_REST_CONTROLEUR,
		RestApplication.PACKAGE_REST_APPLICATION, RestApplication.PACKAGE_METIER_DAO, RestApplication.PACKAGE_METIER_SERVICE,
		RestApplication.PACKAGE_REST_MONITORING })
@EnableJpaRepositories(RestApplication.PACKAGE_METIER_DAO)
@EnableGlobalMethodSecurity
public class RestApplication {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RestApplication.class);

	/** Packages utilisés dans la configuration Spring . */
	public static final String PACKAGE_METIER_DAO = "com.guillaumetalbot.applicationblanche.metier.dao";
	public static final String PACKAGE_METIER_ENTITE = "com.guillaumetalbot.applicationblanche.metier.entite";
	public static final String PACKAGE_METIER_SERVICE = "com.guillaumetalbot.applicationblanche.metier.service";
	public static final String PACKAGE_REST_APPLICATION = "com.guillaumetalbot.applicationblanche.rest.application";
	public static final String PACKAGE_REST_CONTROLEUR = "com.guillaumetalbot.applicationblanche.rest.controleur";
	public static final String PACKAGE_REST_ERREUR = "com.guillaumetalbot.applicationblanche.rest.erreur";
	public static final String PACKAGE_REST_MONITORING = "com.guillaumetalbot.applicationblanche.rest.application.monitoring";
	public static final String PACKAGE_REST_SECURITE = "com.guillaumetalbot.applicationblanche.rest.securite";

	/**
	 * Méthode de démarrage de l'application
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		final ApplicationContext ac = SpringApplication.run(RestApplication.class);

		// Log pour afficher l'URL de l'API
		final String port = ac.getEnvironment().getProperty("server.port");
		final String context = ac.getEnvironment().getProperty("server.servlet.context-path");
		LOG.info("Application disponible sur http://localhost:{}{}", port, context);
	}

	/**
	 * Configuration SpringFox.
	 *
	 * @return SpringFox configuration
	 */
	@Bean
	public Docket configuerSwagger() {

		// see http://springfox.github.io/springfox/docs/current/#springfox-samples
		return new Docket(DocumentationType.SWAGGER_2)//
				.select().apis(RequestHandlerSelectors.any())//
				.paths(PathSelectors.any()).build().pathMapping("/")//
				.directModelSubstitute(LocalDate.class, String.class)//
				.genericModelSubstitutes(ResponseEntity.class)//
				.enableUrlTemplating(true)//
				.tags(new Tag("API Application blanche", "Description de l'API REST"));
	}

	/** Configuration Jackson */
	@Bean
	public com.fasterxml.jackson.databind.Module configurerJackson() {
		final Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.enable(Feature.REPLACE_PERSISTENT_COLLECTIONS);
		hibernate5Module.disable(Feature.USE_TRANSIENT_ANNOTATION);
		return hibernate5Module;
	}
}
