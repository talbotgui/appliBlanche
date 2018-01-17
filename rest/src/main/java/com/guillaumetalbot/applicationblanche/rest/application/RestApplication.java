package com.guillaumetalbot.applicationblanche.rest.application;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.guillaumetalbot.applicationblanche.application.PackageForApplication;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

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
@EntityScan({ PackageForApplication.PACKAGE_METIER_ENTITE })
@ComponentScan({ RestApplication.PACKAGE_REST_CONTROLEUR, RestApplication.PACKAGE_REST_ERREUR, PackageForApplication.PACKAGE_METIER_SERVICE,
		PackageForApplication.PACKAGE_METIER_DAO })
@EnableJpaRepositories(PackageForApplication.PACKAGE_METIER_DAO)
public class RestApplication {

	private static ApplicationContext ac;

	public static final String LOGIN_MDP_ADMIN_PAR_DEFAUT = "adminAsupprimer";
	public static final String PACKAGE_REST_CONTROLEUR = "com.guillaumetalbot.applicationblanche.rest.controleur";
	public static final String PACKAGE_REST_ERREUR = "com.guillaumetalbot.applicationblanche.rest.erreur";

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static void main(final String[] args) {
		start();
	}

	public static void start() {
		ac = SpringApplication.run(RestApplication.class);

		// Si aucun utilisateur au base, on en crée un par défaut
		final SecuriteService securiteService = ac.getBean(SecuriteService.class);
		if (securiteService.listerUtilisateurs().isEmpty()) {
			securiteService.sauvegarderUtilisateur(LOGIN_MDP_ADMIN_PAR_DEFAUT, LOGIN_MDP_ADMIN_PAR_DEFAUT);
		}
	}

	public static void stop() {
		if (ac != null) {
			SpringApplication.exit(ac);
		}
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return container -> {

			// Error pages
			final ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			final ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			final ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
			container.addErrorPages(error401Page, error404Page, error500Page);

			// Mime types
			final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
			container.setMimeMappings(mappings);
		};
	}

	/**
	 * Bean fo SpringFox.
	 *
	 * @return SpringFox configuration
	 */
	@Bean
	public Docket getRestApiDescriptionWithSwagger() {

		// see
		// http://springfox.github.io/springfox/docs/current/#springfox-samples
		return new Docket(DocumentationType.SWAGGER_2)//
				.select().apis(RequestHandlerSelectors.any())//
				.paths(PathSelectors.any()).build().pathMapping("/")//
				.directModelSubstitute(LocalDate.class, String.class)//
				.genericModelSubstitutes(ResponseEntity.class)//
				.enableUrlTemplating(true)//
				.tags(new Tag("API MonMariage", "Description de l'API de l'application MonMariage"));
	}

}