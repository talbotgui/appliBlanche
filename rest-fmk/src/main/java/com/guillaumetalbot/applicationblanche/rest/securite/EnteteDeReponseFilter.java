package com.guillaumetalbot.applicationblanche.rest.securite;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtre ajoutant les entêtes de sécurité.
 *
 * Ce filtre est paramétré depuis {@link WebSecurityConfig} car sa position dans la chaine est déterminante.
 */
public class EnteteDeReponseFilter extends GenericFilterBean {

	private final String accessControlAllowHeaders;

	private final String accessControlExposeHeaders;

	public EnteteDeReponseFilter(final String accessControlAllowHeaders, final String accessControlExposeHeaders) {
		super();
		this.accessControlAllowHeaders = accessControlAllowHeaders;
		this.accessControlExposeHeaders = accessControlExposeHeaders;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequete = (HttpServletRequest) request;
		final HttpServletResponse httpReponse = (HttpServletResponse) response;
		httpReponse.setHeader("Access-Control-Allow-Origin", httpRequete.getHeader("Origin"));
		httpReponse.setHeader("Access-Control-Allow-Headers", this.accessControlAllowHeaders);
		httpReponse.setHeader("Access-Control-Expose-Headers", this.accessControlExposeHeaders);
		httpReponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");

		filterChain.doFilter(request, response);
	}

}
