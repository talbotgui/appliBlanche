package com.guillaumetalbot.applicationblanche.rest.securite;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtre ajoutant les entêtes de sécurité.
 *
 * Ce filtre n'est pas paramétré depuis {@link WebSecurityConfig} car sa position dans la chaine n'est pas déterminante.
 */
@Component
public class EnteteDeReponseFilter extends OncePerRequestFilter {

	@Value("${security.headers.accessControlAllowHeaders}")
	private String accessControlAllowHeaders;

	@Value("${security.headers.accessControlExposeHeaders}")
	private String accessControlExposeHeaders;

	@Override
	protected void doFilterInternal(final HttpServletRequest httpRequete, final HttpServletResponse httpReponse, final FilterChain filterChain)
			throws ServletException, IOException {
		httpReponse.setHeader("Access-Control-Allow-Origin", httpRequete.getHeader("Origin"));
		httpReponse.setHeader("Access-Control-Allow-Headers", this.accessControlAllowHeaders);
		httpReponse.setHeader("Access-Control-Expose-Headers", this.accessControlExposeHeaders);
		httpReponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");

		filterChain.doFilter(httpRequete, httpReponse);
	}

}
