package com.guillaumetalbot.applicationblanche.metier.entite.client;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Dossier extends Entite {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;

	private LocalDateTime dateCreation;

	@OneToMany(mappedBy = "dossier")
	private Set<Demande> demandes;

	private String nom;

	public Dossier() {
		super();
	}

	public Dossier(final String nom) {
		super();
		this.nom = nom;
	}

	public Dossier(final String reference, final String nom) {
		super();
		this.nom = nom;
		this.setReference(reference);
	}

	public Client getClient() {
		return this.client;
	}

	public LocalDateTime getDateCreation() {
		return this.dateCreation;
	}

	public Set<Demande> getDemandes() {
		return MutableUtil.getMutable(this.demandes);
	}

	public String getNom() {
		return this.nom;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setDateCreation(final LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}
}
