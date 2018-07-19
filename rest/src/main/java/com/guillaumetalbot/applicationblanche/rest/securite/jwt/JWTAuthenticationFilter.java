package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.JwtException;
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
		final HttpServletRequest httpRequete = (HttpServletRequest) request;

		// Récupération de la description de l'utilisateur connecté
		String token = httpRequete.getHeader(this.parametresJwt.getHeaderKey());
		if (token != null) {
			token = token.replace(this.parametresJwt.getTokenPrefix(), "");

			try {
				// parse du token
				final String login = Jwts.parser().setSigningKey(this.parametresJwt.getSecret()).parseClaimsJws(token).getBody().getSubject();

				// Si on a un login
				if (login != null) {
					// Sauvegarde de cette information dans le contexte de la requête
					final Authentication authentication = new UsernamePasswordAuthenticationToken(login, null, Collections.emptyList());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					LOG.warn("Requête {} avec token invalide en entête (pas de login)", httpRequete.getServletPath());
				}

			} catch (final JwtException e) {
				// En cas d'erreur de parse du token
				LOG.warn("Requête {} avec token invalide en entête ({})", httpRequete.getServletPath(), e.getMessage());
			}
		} else if (!HttpMethod.OPTIONS.name().equals(httpRequete.getMethod())) {
			LOG.info("Requête {} sans token JWT en entête", httpRequete.getServletPath());
		}

		// Le filtre a fini son boulo. Si cette URL est protégée, Spring refusera l'accès à la ressource
		filterChain.doFilter(request, response);
	}
}