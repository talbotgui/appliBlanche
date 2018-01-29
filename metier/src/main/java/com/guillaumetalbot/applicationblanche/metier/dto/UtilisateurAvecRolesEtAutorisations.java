package com.guillaumetalbot.applicationblanche.metier.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

public class UtilisateurAvecRolesEtAutorisations implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;

	private Collection<String> ressources;

	private Collection<String> roles;

	public UtilisateurAvecRolesEtAutorisations() {
		super();
	}

	public UtilisateurAvecRolesEtAutorisations(final Utilisateur u) {
		super();
		this.login = u.getLogin();
		this.roles = new ArrayList<>();
		this.ressources = new ArrayList<>();
		for (final Role r : u.getRoles()) {
			this.roles.add(r.getNom());
			for (final Ressource re : r.getRessourcesAutorisees()) {
				this.ressources.add(re.getClef());
			}
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
