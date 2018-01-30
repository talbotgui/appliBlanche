package com.guillaumetalbot.applicationblanche.metier.entite.client;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Client extends Entite {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CLIENT_ID", unique = true)
	private Adresse adresse;

	private Date dateCreation;

	@OneToMany(mappedBy = "client")
	private Set<Dossier> dossiers;

	private String nom;

	public Client() {
		super();
	}

	public Client(final String nom) {
		super();
		this.nom = nom;
	}

	public Adresse getAdresse() {
		return this.adresse;
	}

	public Date getDateCreation() {
		return MutableUtil.getMutable(this.dateCreation);
	}

	public Set<Dossier> getDossiers() {
		return MutableUtil.getMutable(this.dossiers);
	}

	public String getNom() {
		return this.nom;
	}

	public void setAdresse(final Adresse adresse) {
		this.adresse = adresse;
	}

	public void setDateCreation(final Date dateCreation) {
		this.dateCreation = MutableUtil.getMutable(dateCreation);
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}
}
