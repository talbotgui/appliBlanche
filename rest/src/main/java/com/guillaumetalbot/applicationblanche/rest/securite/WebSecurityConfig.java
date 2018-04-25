package com.guillaumetalbot.applicationblanche.rest.securite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.guillaumetalbot.applicationblanche.rest.securite.jwt.JWTAuthenticationFilter;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.JWTConnexionFilter;
import com.guillaumetalbot.applicationblanche.rest.securite.jwt.ParametresJwt;

/** Configuration de la sécurité de l'application */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/** Contenu du fichier application.properties lié à JWT. */
	@Autowired
	private ParametresJwt parametresJwt;

	/** Clef de configuration présente dans application.properties */
	@Value("${springfox.documentation.swagger.v2.path}")
	private String swaggerDocsPath;

	/** Composant mappant le service métier et les interfaces de Spring. */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Méthode appelée à l'authentification et faisant appel au service métier.
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService);
	}

	/**
	 * Définition de toute la sécurité de l'application
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		// Désactivation du CSRF
		http.csrf().disable()
				// La page index.html est accessible
				.authorizeRequests().antMatchers("/").permitAll()
				// les écrans de Swagger sont accessibles à tous
				.antMatchers(this.swaggerDocsPath).permitAll()
				// les API publiques sont accessibles
				.antMatchers("/i18n/**").permitAll()//
				// Le filtre de connexion JWT est accessble
				.antMatchers(HttpMethod.POST, this.parametresJwt.getUrlConnexion()).permitAll()
				.antMatchers(HttpMethod.OPTIONS, this.parametresJwt.getUrlConnexion()).permitAll()
				// Tout le reste est protégé
				.anyRequest().authenticated().and()
				// Ajout du filtre permettant la connexion JWT
				.addFilterBefore(new JWTConnexionFilter(this.parametresJwt, this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				// Ajout du filtre vérifiant la présence du token JWT
				.addFilterBefore(new JWTAuthenticationFilter(this.parametresJwt), UsernamePasswordAuthenticationFilter.class);
	}
}