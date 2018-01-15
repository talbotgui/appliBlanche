package com.guillaumetalbot.applicationblanche.metier.entite.client;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Client {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CLIENT_ID", unique = true)
	private Adresse adresse;

	private Date dateCreation;

	@OneToMany(mappedBy = "client")
	private Set<Dossier> dossiers;

	@Id
	@GeneratedValue
	private Long id;

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

	public Long getId() {
		return this.id;
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

	public void setDossiers(final Set<Dossier> dossiers) {
		this.dossiers = MutableUtil.getMutable(dossiers);
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}
}
