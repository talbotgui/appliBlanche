package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class LienRoleRessourceId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESSOURCE_CLEF")
	private Ressource ressource;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_NOM")
	private Role role;

	public LienRoleRessourceId() {
		super();
	}

	public LienRoleRessourceId(final Role role, final Ressource ressource) {
		super();
		this.role = role;
		this.ressource = ressource;
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

	public Ressource getRessource() {
		return this.ressource;
	}

	public Role getRole() {
		return this.role;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = new HashCodeBuilder();
		if (this.role != null) {
			hcb.append(this.role.getNom());
		}
		if (this.ressource != null) {
			hcb.append(this.ressource.getClef());
		}
		return hcb.toHashCode();
	}

	public void setRessource(final Ressource ressource) {
		this.ressource = ressource;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

}
