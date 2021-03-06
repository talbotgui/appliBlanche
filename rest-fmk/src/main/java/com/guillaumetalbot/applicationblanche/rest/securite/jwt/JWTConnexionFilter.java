package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.rest.securite.UserDetailsServiceWrapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Filtre HTTP permettant à un utilisateur de se connecter. Ce filtre n'est actif que pour l'URL "/login" (cf. WebSecurityConfig.java)
 */
public class JWTConnexionFilter extends AbstractAuthenticationProcessingFilter {

	public static final String JWSTOKEN_KEY_IP_CLIENT = "ipClient";
	public static final String JWSTOKEN_KEY_RESSOURCES_AUTORISEES = "ressourcesAutorisees";

	private static final Logger LOG = LoggerFactory.getLogger(JWTConnexionFilter.class);

	private static final String PARAMETRE_LOGIN_EN_REQUETE = "login";

	/** Paramètres JWT présents dans les application.properties */
	private final ParametresJwt parametresJwt;

	/** Service métier traitant de la sécurité */
	private final UserDetailsServiceWrapper securiteService;

	/**
	 * Constructeur
	 *
	 * @param parametresJwt
	 * @param authManager
	 * @param securiteService
	 */
	public JWTConnexionFilter(final ParametresJwt parametresJwt, final AuthenticationManager authManager,
			final UserDetailsServiceWrapper securiteService) {
		super(new AntPathRequestMatcher(parametresJwt.getUrlConnexion(), HttpMethod.POST.name()));
		this.setAuthenticationManager(authManager);
		this.parametresJwt = parametresJwt;
		this.securiteService = securiteService;
	}

	/**
	 * Méthode appelée quand une requête correspond à l'URL.
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res) throws IOException, ServletException {
		// lecture du corp de la requête pour y récupérer un objet de type ParametreDeConnexionDto
		final ParametreDeConnexionDto param = new ObjectMapper().readValue(req.getInputStream(), ParametreDeConnexionDto.class);

		// Log
		LOG.debug("Tentative de connexion de {}", param.getLogin());

		// Ajout du login dans la requete pour le récupérer en cas d'echec
		req.setAttribute(PARAMETRE_LOGIN_EN_REQUETE, param.getLogin());

		// Appel au composant d'authentification avec les paramètres de connexion
		return this.getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(param.getLogin(), param.getMdp(), Collections.emptyList()));
	}

	/**
	 * En cas d'authentification réussie de l'utilisateur
	 */
	@Override
	protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain,
			final Authentication auth) throws IOException, ServletException {

		// on charge les données de l'utilisateur
		final String login = auth.getName();

		// Lecture de l'IP
		String ipClient = req.getHeader(HttpHeaders.X_FORWARDED_FOR);
		if (ipClient == null) {
			ipClient = req.getRemoteAddr();
		}

		// Log
		LOG.debug("Connexion de {} réussie (IP = {})", login, ipClient);

		final Utilisateur utilisateur = this.securiteService.chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(login);
		final Collection<String> ressourcesAutorisees = new ArrayList<>();
		if (utilisateur.getRoles() != null) {
			for (final Role role : utilisateur.getRoles()) {
				ressourcesAutorisees
						.addAll(role.getRessourcesAutorisees().stream().map(re -> re.getClef().toUpperCase()).collect(Collectors.toList()));
			}
		}

		// Log
		LOG.debug("Ressources autorisées à {} : {}", login, ressourcesAutorisees);

		// on crée le token et on l'ajoute dans les entêtes de la réponse.
		final String JWT = Jwts.builder().setSubject(login)//
				.setExpiration(new Date(System.currentTimeMillis() + this.parametresJwt.getExpirationTime()))//
				.signWith(SignatureAlgorithm.HS512, this.parametresJwt.getSecret())//
				.claim(JWSTOKEN_KEY_RESSOURCES_AUTORISEES, ressourcesAutorisees)//
				.claim(JWSTOKEN_KEY_IP_CLIENT, ipClient).compact();
		res.addHeader(this.parametresJwt.getHeaderKey(), this.parametresJwt.getTokenPrefix() + " " + JWT);

		// on notifie le service métier de la bonne connexion
		this.securiteService.notifierConnexion(login, true);

		// On ajoute l'utilisateur, ses roles et ressources autorisées dans la réponse
		res.setContentType("application/json");
		final String json = new ObjectMapper().writeValueAsString(utilisateur);
		res.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
		res.getOutputStream().flush();
	}

	/**
	 * En cas d'echec de connexion
	 */
	@Override
	protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException failed) throws IOException, ServletException {

		// Ajout des entêtes
		super.unsuccessfulAuthentication(request, response, failed);

		// on notifie le service métier de la bonne connexion
		final String login = (String) request.getAttribute(PARAMETRE_LOGIN_EN_REQUETE);
		this.securiteService.notifierConnexion(login, false);

		// Log
		LOG.debug("Connexion de {} échouée", login);
	}

}