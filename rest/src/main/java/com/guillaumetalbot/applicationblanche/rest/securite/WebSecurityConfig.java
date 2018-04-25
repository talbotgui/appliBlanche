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

		http.csrf().disable()//
				.authorizeRequests().antMatchers("/").permitAll()//
				.antMatchers(HttpMethod.POST, "/login").permitAll()//
				.anyRequest().authenticated().and()
				// Ajout du filtre permettant la connexion JWT
				.addFilterBefore(new JWTConnexionFilter(this.parametresJwt, this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				// Ajout du filtre vérifiant la présence du token JWT
				.addFilterBefore(new JWTAuthenticationFilter(this.parametresJwt), UsernamePasswordAuthenticationFilter.class);
	}
}