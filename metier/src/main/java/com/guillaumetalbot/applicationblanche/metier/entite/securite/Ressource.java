package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ressource {

	@Id
	private String clef;

	private String description;

	public Ressource() {
		super();
	}

	public Ressource(final String clef) {
		super();
		this.clef = clef;
	}

	public Ressource(final String clef, final String description) {
		super();
		this.clef = clef;
		this.description = description;
	}

	public String getClef() {
		return this.clef;
	}

	public String getDescription() {
		return this.description;
	}
}
