package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

/**
 * Filtre de sécurité vérifiant la présence d'un token JWT
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private final ParametresJwt parametresJwt;

	public JWTAuthenticationFilter(final ParametresJwt parametresJwt) {
		super();
		this.parametresJwt = parametresJwt;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequete = ((HttpServletRequest) request);
		final HttpServletResponse httpReponse = ((HttpServletResponse) response);

		// Récupération de la description de l'utilisateur connecté
		// Null si pas de token
		String token = httpRequete.getHeader(this.parametresJwt.getHeaderKey());
		Authentication authentication = null;
		if (token != null) {
			token = token.replace(this.parametresJwt.getTokenPrefix(), "");
			final String login = Jwts.parser().setSigningKey(this.parametresJwt.getSecret()).parseClaimsJws(token).getBody().getSubject();
			if (login != null) {
				authentication = new UsernamePasswordAuthenticationToken(login, null, Collections.emptyList());
			}
		}

		// log
		if (LOG.isInfoEnabled() && (authentication == null)) {
			LOG.info("Requête {} bloquée car aucun token valide en entête", httpRequete.getServletPath());
		}

		// Sauvegarde de cette information dans le contexte de la requête
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Ajout des entêtes de sécurité
		httpReponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
		httpReponse.setHeader("Access-Control-Allow-Origin", httpRequete.getHeader("Origin"));
		httpReponse.setHeader("Access-Control-Expose-Headers", "Authorization");

		// Le filtre a fini son boulo. Si cette URL est protégée, Spring refusera l'accès à la ressource
		filterChain.doFilter(request, response);
	}
}