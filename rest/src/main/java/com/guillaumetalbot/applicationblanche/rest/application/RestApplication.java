package com.guillaumetalbot.applicationblanche.rest.application;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
@EnableGlobalMethodSecurity
public class RestApplication {

	/** Contexte applicatif démarré. */
	private static ApplicationContext ac;

	/** Configuration de la sécurité par défaut. */
	public static final String ADMIN_PAR_DEFAUT_LOGIN_MDP = "adminAsupprimer";
	public static final String ADMIN_PAR_DEFAUT_ROLE = "administrateur";

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RestApplication.class);

	/** Custom MIME-TYPE. */
	public static final String MIME_JSON_DETAILS = "application/json;details";

	/** Packages utilisés dans la configuration Spring. */
	public static final String PACKAGE_REST_CONTROLEUR = "com.guillaumetalbot.applicationblanche.rest.controleur";
	public static final String PACKAGE_REST_ERREUR = "com.guillaumetalbot.applicationblanche.rest.erreur";

	/**
	 * Recherche de tous les controleurs REST et des méthodes qu'ils exposent.
	 *
	 * @return
	 */
	private static Collection<String> listerMethodesDeControleurs() {
		final Map<String, Object> beans = ac.getBeansWithAnnotation(Controller.class);
		final Collection<String> clefsRessources = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : beans.entrySet()) {
			final String nomClasse = entry.getKey() + ".";
			final Object bean = entry.getValue();
			if (!PACKAGE_REST_CONTROLEUR.equals(bean.getClass().getPackage().getName())) {
				continue;
			}
			for (final Method methode : entry.getValue().getClass().getDeclaredMethods()) {
				clefsRessources.add(nomClasse + methode.getName());
			}
		}
		return clefsRessources;
	}

	/**
	 * Méthode de démarrage de l'application
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		ac = SpringApplication.run(RestApplication.class);

		// Création/complétion des clefs de ressource en fonction des méthodes exposées par l'API
		// Si aucun utilisateur au base, on en crée un par défaut associé au role 'administrateur' et ayant tous les droits
		final SecuriteService securiteService = ac.getBean(SecuriteService.class);

		final Collection<String> clefsRessources = listerMethodesDeControleurs();
		securiteService.initialiserOuCompleterConfigurationSecurite(clefsRessources, ADMIN_PAR_DEFAUT_LOGIN_MDP, ADMIN_PAR_DEFAUT_LOGIN_MDP,
				ADMIN_PAR_DEFAUT_ROLE);

		// Log pour afficher l'URL de l'API
		final String port = ac.getEnvironment().getProperty("server.port");
		final String context = ac.getEnvironment().getProperty("server.context-path");
		LOG.info("Application disponible sur http://localhost:{}/{}", port, context);
	}

	/**
	 * Méthode d'arrêt de l'application
	 */
	public static void stop() {
		if (ac != null) {
			SpringApplication.exit(ac);
		}
	}

	/**
	 * Configuration SpringFox.
	 *
	 * @return SpringFox configuration
	 */
	@Bean
	public Docket configuerSwagger() {

		// see
		// http://springfox.github.io/springfox/docs/current/#springfox-samples
		return new Docket(DocumentationType.SWAGGER_2)//
				.select().apis(RequestHandlerSelectors.any())//
				.paths(PathSelectors.any()).build().pathMapping("/")//
				.directModelSubstitute(LocalDate.class, String.class)//
				.genericModelSubstitutes(ResponseEntity.class)//
				.enableUrlTemplating(true)//
				.tags(new Tag("API Application blanche", "Description de l'API REST"));
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(RestApplication.ADMIN_PAR_DEFAUT_LOGIN_MDP).password(RestApplication.ADMIN_PAR_DEFAUT_LOGIN_MDP)
				.roles("USER");
	}

	/**
	 * Configuration des pages HTML du répertoire src/main/resources/templates.
	 *
	 * @return
	 */
	@Bean
	public WebMvcConfigurerAdapter configurerPagesHtml() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addViewControllers(final ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("index");
			}
		};
	}

	@Bean
	public EmbeddedServletContainerCustomizer creerPagesErreur() {
		return container -> {

			// Error pages
			final ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			final ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
			final ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			final ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
			final ErrorPage errorPage = new ErrorPage("/500.html");
			container.addErrorPages(error401Page, error403Page, error404Page, error500Page, errorPage);

			// Mime types
			final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
			container.setMimeMappings(mappings);
		};
	}
}
