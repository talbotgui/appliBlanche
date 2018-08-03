package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ressource {

	private String chemin;

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
		this(clef);
		this.description = description;
	}

	public Ressource(final String clef, final String chemin, final String description) {
		this(clef, description);
		this.chemin = chemin;
	}

	public int comparerClefEtChemin(final Ressource o2) {
		return (this.clef + "|" + this.chemin).compareTo(o2.clef + "|" + o2.chemin);
	}

	public String getChemin() {
		return this.chemin;
	}

	public String getClef() {
		return this.clef;
	}

	public String getDescription() {
		return this.description;
	}
}
