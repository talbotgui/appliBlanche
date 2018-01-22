package com.guillaumetalbot.applicationblanche.rest.application;

import java.time.LocalDate;

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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
@EnableWebSecurity
public class RestApplication {

	/** Contexte applicatif démarré. */
	private static ApplicationContext ac;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RestApplication.class);

	/** Packages utilisés dans la configuration Spring. */
	public static final String LOGIN_MDP_ADMIN_PAR_DEFAUT = "adminAsupprimer";
	public static final String PACKAGE_REST_CONTROLEUR = "com.guillaumetalbot.applicationblanche.rest.controleur";
	public static final String PACKAGE_REST_ERREUR = "com.guillaumetalbot.applicationblanche.rest.erreur";

	private static final String[] URI_SWAGGER = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**" };

	/**
	 * Méthode de démarrage de l'application
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		ac = SpringApplication.run(RestApplication.class);

		// Si aucun utilisateur au base, on en crée un par défaut
		final SecuriteService securiteService = ac.getBean(SecuriteService.class);
		if (securiteService.listerUtilisateurs().isEmpty()) {
			securiteService.sauvegarderUtilisateur(LOGIN_MDP_ADMIN_PAR_DEFAUT, LOGIN_MDP_ADMIN_PAR_DEFAUT);
		}

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

	/**
	 * Configuration pour accepter les appels inter-origines
	 *
	 * @return
	 */
	@Bean
	public WebMvcConfigurer configurerCors() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
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
				registry.addViewController("/login").setViewName("login");
				registry.addViewController("/error").setViewName("error");

			}
		};
	}

	@Bean
	public WebSecurityConfigurerAdapter configurerSpringSecurity() {
		return new WebSecurityConfigurerAdapter() {
			@Override
			protected void configure(final HttpSecurity http) throws Exception {

				http
						// Tout le monde a accès à l'API décrite dans Swagger
						.authorizeRequests().antMatchers(URI_SWAGGER).anonymous()

						// Par défaut, tout est protégé par l'authentification
						.and().authorizeRequests().antMatchers("/", "/home").permitAll().anyRequest().authenticated()

						// Tout le monde a accès à login
						// au succès du login, l'utilisateur est renvoyé vers la précédente page sécurisée (ligne juste au dessus)
						.and().formLogin().loginPage("/login").permitAll()

						// Tout le monde a accès à logout
						.and().logout().permitAll();

			}

			@Autowired
			public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
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
