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

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Une configuration Spring-boot pour l'application. Cette classe remplace le traditionnel fichier XML.
 */
@SpringBootApplication
@EnableSwagger2
@EntityScan("com.guillaumetalbot.applicationblanche.metier.entite")
@ComponentScan({ "com.guillaumetalbot.applicationblanche.rest.erreur", "com.guillaumetalbot.applicationblanche.rest.securite",
		RestApplicationForTests.PACKAGE_CONTROLEUR, "com.guillaumetalbot.applicationblanche.metier.dao",
		"com.guillaumetalbot.applicationblanche.metier.service", "com.guillaumetalbot.applicationblanche.rest.application.monitoring" })
@EnableJpaRepositories("com.guillaumetalbot.applicationblanche.metier.dao")
@EnableGlobalMethodSecurity
public class RestApplicationForTests {

	/** Contexte applicatif démarré. */
	private static ApplicationContext ac;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RestApplicationForTests.class);

	public static final String PACKAGE_CONTROLEUR = "com.guillaumetalbot.applicationblanche.rest.controleur";

	/**
	 * Méthode de démarrage de l'application
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		ac = SpringApplication.run(RestApplicationForTests.class);

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
}
