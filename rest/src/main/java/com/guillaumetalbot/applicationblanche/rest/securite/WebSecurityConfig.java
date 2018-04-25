package com.guillaumetalbot.applicationblanche.rest.securite;

import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ParametresJwt parametresJwt;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Méthode appelée à l'authentification et faisant appel au service métier.
	 */
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		// Désactivation du CSRF
		http.csrf().disable()
				// La page index.html est accessible
				.authorizeRequests().antMatchers("/").permitAll()
				// les écrans de Swagger sont accessibles à tous
				.antMatchers("/v2/api-docs", "/swagger-ui.html", "/swagger-resources/").permitAll()
				// les API publiques sont accessibles
				.antMatchers("/i18n/**").permitAll()//
				// Le filtre de connexion JWT est accessble
				.antMatchers(HttpMethod.POST, "/login").permitAll()//
				// Tout le reste est protégé
				.anyRequest().authenticated().and()
				// Ajout du filtre permettant la connexion JWT
				.addFilterBefore(new JWTConnexionFilter(this.parametresJwt, this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				// Ajout du filtre vérifiant la présence du token JWT
				.addFilterBefore(new JWTAuthenticationFilter(this.parametresJwt), UsernamePasswordAuthenticationFilter.class);
	}
}