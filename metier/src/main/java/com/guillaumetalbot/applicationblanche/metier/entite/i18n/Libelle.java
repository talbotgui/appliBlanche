package com.guillaumetalbot.applicationblanche.metier.entite.i18n;

import javax.persistence.Entity;

import com.guillaumetalbot.applicationblanche.metier.entite.Entite;

@Entity
public class Libelle extends Entite {
	private static final long serialVersionUID = 1L;

	private String clef;

	private String langue;

	private String libelle;

	public String getClef() {
		return this.clef;
	}

	public String getLangue() {
		return this.langue;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setClef(final String clef) {
		this.clef = clef;
	}

	public void setLangue(final String langue) {
		this.langue = langue;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

}
