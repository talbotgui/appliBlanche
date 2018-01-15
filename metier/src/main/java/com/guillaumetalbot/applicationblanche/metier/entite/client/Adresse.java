package com.guillaumetalbot.applicationblanche.metier.entite.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Adresse {

	private String codePostal;

	@Id
	@GeneratedValue
	private Long id;

	private String rue;

	private String ville;

	public Adresse() {
		super();
	}

	public Adresse(final Long id, final String rue, final String codePostal, final String ville) {
		super();
		this.id = id;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Adresse(final String rue, final String codePostal, final String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public Long getId() {
		return this.id;
	}

	public String getRue() {
		return this.rue;
	}

	public String getVille() {
		return this.ville;
	}

	public void setCodePostal(final String codePostal) {
		this.codePostal = codePostal;
	}

	public void setRue(final String rue) {
		this.rue = rue;
	}

	public void setVille(final String ville) {
		this.ville = ville;
	}
}
