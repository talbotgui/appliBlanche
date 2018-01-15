package com.guillaumetalbot.applicationblanche.metier.entite;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role {

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
		if (this.ressourcesAutorisees == null) {
			return new HashSet<>();
		}
		return new HashSet<>(this.ressourcesAutorisees);
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public void setRessourcesAutorisees(final Set<Ressource> ressourcesAutorisees) {
		this.ressourcesAutorisees = new HashSet<>(ressourcesAutorisees);
	}

}
