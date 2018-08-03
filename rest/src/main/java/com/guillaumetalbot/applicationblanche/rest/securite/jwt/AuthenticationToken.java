package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Cette classe est le type utilisé par SpringSecurity en tant que token. Il contient les données de l'utilisateur connecté et est accessible via
 * "SecurityContextHolder.getContext().getAuthentication()"
 */
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;

	private final Collection<String> ressourcesAutorisees;

	public AuthenticationToken(final Object principal, final Object credentials, final Collection<String> ressourcesAutorisees) {
		super(principal, credentials, Collections.emptyList());
		this.ressourcesAutorisees = ressourcesAutorisees;
	}

	public Collection<String> getRessourcesAutorisees() {
		return this.ressourcesAutorisees;
	}

}
