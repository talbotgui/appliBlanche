package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

/**
 * Filtre de sécurité vérifiant la présence d'un token JWT
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

	private final ParametresJwt parametresJwt;

	public JWTAuthenticationFilter(final ParametresJwt parametresJwt) {
		super();
		this.parametresJwt = parametresJwt;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		// Récupération de la description de l'utilisateur connecté
		// Null si pas de token
		String token = ((HttpServletRequest) request).getHeader(this.parametresJwt.getHeaderKey());
		Authentication authentication = null;
		if (token != null) {
			token = token.replace(this.parametresJwt.getTokenPrefix(), "");
			final String login = Jwts.parser().setSigningKey(this.parametresJwt.getSecret()).parseClaimsJws(token).getBody().getSubject();
			if (login != null) {
				authentication = new UsernamePasswordAuthenticationToken(login, null, Collections.emptyList());
			}
		}

		// Sauvegarde de cette information dans le contexte de la requête
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Le filtre a fini son boulo. Si cette URL est protégée, Spring refusera l'accès à la ressource
		filterChain.doFilter(request, response);
	}
}