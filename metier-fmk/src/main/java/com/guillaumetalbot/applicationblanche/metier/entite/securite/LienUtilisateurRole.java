package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIEN_UTILISATEUR_ROLE")
public class LienUtilisateurRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private LienUtilisateurRoleId id;

	public LienUtilisateurRole() {
		super();
	}

	public LienUtilisateurRole(final Role role, final Utilisateur utilisateur) {
		super();
		this.id = new LienUtilisateurRoleId(role, utilisateur);
	}

	/** Méthode simplifiant l'accès à la donnée. */
	public Role getRole() {
		if (this.id == null) {
			return null;
		}
		return this.id.getRole();
	}

	/** Méthode simplifiant l'accès à la donnée. */
	public Utilisateur getUtilisateur() {
		if (this.id == null) {
			return null;
		}
		return this.id.getUtilisateur();
	}
}
