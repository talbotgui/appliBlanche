package com.guillaumetalbot.applicationblanche.batch.csvclient.dto;

public class LigneCsvImportClient {

	private String codePostal;
	private String nomClient;
	private String rue;
	private String ville;

	public String getCodePostal() {
		return this.codePostal;
	}

	public String getNomClient() {
		return this.nomClient;
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

	public void setNomClient(final String nomClient) {
		this.nomClient = nomClient;
	}

	public void setRue(final String rue) {
		this.rue = rue;
	}

	public void setVille(final String ville) {
		this.ville = ville;
	}
}
