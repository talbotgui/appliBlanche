package com.guillaumetalbot.applicationblanche.metier.entite;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIEN_ROLE_RESSOURCE")
public class LienRoleRessource {

	@Id
	private LienRoleRessourceId id;

	public LienRoleRessource() {
		super();
	}

	public LienRoleRessource(final Role role, final Ressource ressource) {
		super();
		this.id = new LienRoleRessourceId(role, ressource);
	}

	/** Méthode simplifiant l'accès à la donnée. */
	public Ressource getRessource() {
		if (this.id == null) {
			return null;
		}
		return this.id.getRessource();
	}

	/** Méthode simplifiant l'accès à la donnée. */
	public Role getRole() {
		if (this.id == null) {
			return null;
		}
		return this.id.getRole();
	}
}
