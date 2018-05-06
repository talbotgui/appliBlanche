package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillaumetalbot.applicationblanche.rest.securite.UserDetailsServiceWrapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Filtre HTTP permettant à un utilisateur de se connecter. Ce filtre n'est actif que pour l'URL "/login" (cf. WebSecurityConfig.java)
 */
public class JWTConnexionFilter extends AbstractAuthenticationProcessingFilter {

	/** Paramètres JWT présents dans les application.properties */
	private final ParametresJwt parametresJwt;

	/** Service métier traitant de la sécurité */
	private final UserDetailsServiceWrapper securiteService;

	/**
	 * Constructeur.
	 *
	 * @param url
	 *            URL sur laquelle se déclenchera ce filtre.
	 * @param authManager
	 *            Instance de composant permetant la vérification du login/mdp.
	 */
	public JWTConnexionFilter(final ParametresJwt parametresJwt, final AuthenticationManager authManager,
			final UserDetailsServiceWrapper securiteService) {
		super(new AntPathRequestMatcher(parametresJwt.getUrlConnexion(), "POST"));
		this.setAuthenticationManager(authManager);
		this.parametresJwt = parametresJwt;
		this.securiteService = securiteService;
	}

	/**
	 * Méthode appelée quand une requête correspond à l'URL.
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		// lecture du corp de la requête pour y récupérer un objet de type ParametreDeConnexionDto
		final ParametreDeConnexionDto param = new ObjectMapper().readValue(req.getInputStream(), ParametreDeConnexionDto.class);

		// Ajout du login dans la requete pour le récupérer en cas d'echec
		req.setAttribute("login", param.getLogin());

		// Appel au composant d'authentification avec les paramètres de connexion
		return this.getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(param.getLogin(), param.getMdp(), Collections.emptyList()));
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequete = (HttpServletRequest) request;
		final HttpServletResponse httpReponse = (HttpServletResponse) response;

		// Ajout des entêtes de sécurité
		httpReponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
		httpReponse.setHeader("Access-Control-Allow-Origin", httpRequete.getHeader("Origin"));
		httpReponse.setHeader("Access-Control-Expose-Headers", "Authorization");

		super.doFilter(request, response, chain);
	}

	/**
	 * En cas d'authentification réussie de l'utilisateur
	 */
	@Override
	protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain,
			final Authentication auth) throws IOException, ServletException {

		// on crée le token et on l'ajoute dans les entêtes de la réponse.
		final String login = auth.getName();
		final String JWT = Jwts.builder().setSubject(login)//
				.setExpiration(new Date(System.currentTimeMillis() + this.parametresJwt.getExpirationTime()))//
				.signWith(SignatureAlgorithm.HS512, this.parametresJwt.getSecret())//
				.compact();
		res.addHeader(this.parametresJwt.getHeaderKey(), this.parametresJwt.getTokenPrefix() + " " + JWT);

		// on notifie le service métier de la bonne connexion
		this.securiteService.notifierConnexion(login, true);
	}

	/**
	 * En cas d'echec de connexion
	 */
	@Override
	protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);

		// on notifie le service métier de la bonne connexion
		final String login = (String) request.getAttribute("login");
		this.securiteService.notifierConnexion(login, false);

	}

}