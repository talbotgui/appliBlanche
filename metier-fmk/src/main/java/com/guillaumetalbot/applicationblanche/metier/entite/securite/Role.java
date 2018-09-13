package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nom;

	@ManyToMany
	@JoinTable(//
			name = "LIEN_ROLE_RESSOURCE", //
			joinColumns = { @JoinColumn(name = "ROLE_NOM", insertable = false, updatable = false) }, //
			inverseJoinColumns = { @JoinColumn(name = "RESSOURCE_CLEF", insertable = false, updatable = false) })
	private Set<Ressource> ressourcesAutorisees;

	public Role() {
		super();
	}

	public Role(final String nom) {
		super();
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}

	public Set<Ressource> getRessourcesAutorisees() {
		return MutableUtil.getMutable(this.ressourcesAutorisees);
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setRessourcesAutorisees(final Set<Ressource> ressourcesAutorisees) {
		this.ressourcesAutorisees = ressourcesAutorisees;
	}
}
