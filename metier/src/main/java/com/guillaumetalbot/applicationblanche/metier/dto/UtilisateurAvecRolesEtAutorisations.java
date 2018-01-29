package com.guillaumetalbot.applicationblanche.metier.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.util.Arrays;

public class UtilisateurAvecRolesEtAutorisations implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;

	private Collection<String> ressources;

	private Collection<String> roles;

	// 2 warning normaux car Arrays.asList renvoie List<Object>
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UtilisateurAvecRolesEtAutorisations(final String login, final String roles, final String ressources) {
		super();
		this.login = login;
		if (roles != null) {
			this.roles = new ArrayList(Arrays.asList(roles.split(",")));
		}
		if (ressources != null) {
			this.ressources = new ArrayList(Arrays.asList(ressources.split(",")));
		}
	}

	public String getLogin() {
		return this.login;
	}

	public Collection<String> getRessources() {
		return this.ressources;
	}

	public Collection<String> getRoles() {
		return this.roles;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setRessources(final Collection<String> ressources) {
		this.ressources = ressources;
	}

	public void setRoles(final Collection<String> roles) {
		this.roles = roles;
	}

}
