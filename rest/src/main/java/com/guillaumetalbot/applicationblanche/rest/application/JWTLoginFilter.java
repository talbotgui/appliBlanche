package com.guillaumetalbot.applicationblanche.rest.application;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(final String url, final AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		this.setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		final AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		return this.getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain,
			final Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}
}