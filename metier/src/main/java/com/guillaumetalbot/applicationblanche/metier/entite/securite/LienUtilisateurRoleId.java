package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class LienUtilisateurRoleId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_NOM")
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UTILISATEUR_LOGIN")
	private Utilisateur utilisateur;

	public LienUtilisateurRoleId() {
		super();
	}

	public LienUtilisateurRoleId(final Role role, final Utilisateur utilisateur) {
		super();
		this.role = role;
		this.utilisateur = utilisateur;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		return this.hashCode() == obj.hashCode();
	}

	public Role getRole() {
		return this.role;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = new HashCodeBuilder();
		if (this.role != null) {
			hcb.append(this.role.getNom());
		}
		if (this.utilisateur != null) {
			hcb.append(this.utilisateur.getLogin());
		}
		return hcb.toHashCode();
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public void setUtilisateur(final Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
