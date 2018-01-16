package com.guillaumetalbot.applicationblanche.metier.entite.client;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Demande {
	private String descriptionCourte;

	private String descriptionLongue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOSSIER_ID")
	private Dossier dossier;

	@Id
	@GeneratedValue
	private Long id;

	public Demande() {
		super();
	}

	public Demande(final Long id, final String descriptionCourte, final String descriptionLongue) {
		super();
		this.id = id;
		this.descriptionCourte = descriptionCourte;
		this.descriptionLongue = descriptionLongue;
	}

	public Demande(final String descriptionCourte, final String descriptionLongue) {
		super();
		this.descriptionCourte = descriptionCourte;
		this.descriptionLongue = descriptionLongue;
	}

	public String getDescriptionCourte() {
		return this.descriptionCourte;
	}

	public String getDescriptionLongue() {
		return this.descriptionLongue;
	}

	public Dossier getDossier() {
		return this.dossier;
	}

	public Long getId() {
		return this.id;
	}

	public void setDescriptionCourte(final String descriptionCourte) {
		this.descriptionCourte = descriptionCourte;
	}

	public void setDescriptionLongue(final String descriptionLongue) {
		this.descriptionLongue = descriptionLongue;
	}

	public void setDossier(final Dossier dossier) {
		this.dossier = dossier;
	}

}
