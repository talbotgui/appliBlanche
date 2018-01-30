package com.guillaumetalbot.applicationblanche.metier.entite.client;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Demande extends Entite {
	private static final long serialVersionUID = 1L;

	private String descriptionCourte;

	private String descriptionLongue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOSSIER_ID")
	private Dossier dossier;

	public Demande() {
		super();
	}

	public Demande(final String descriptionCourte, final String descriptionLongue) {
		super();
		this.descriptionCourte = descriptionCourte;
		this.descriptionLongue = descriptionLongue;
	}

	public Demande(final String reference, final String descriptionCourte, final String descriptionLongue) {
		super();
		this.descriptionCourte = descriptionCourte;
		this.descriptionLongue = descriptionLongue;
		this.setReference(reference);
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
