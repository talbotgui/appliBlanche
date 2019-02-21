package com.guillaumetalbot.applicationblanche.rest.dto;

public class UtilisateurAcreerDto {

	private String login;
	private String mdp;

	public UtilisateurAcreerDto() {
		super();
	}

	public UtilisateurAcreerDto(final String login, final String mdp) {
		super();
		this.login = login;
		this.mdp = mdp;
	}

	public String getLogin() {
		return this.login;
	}

	public String getMdp() {
		return this.mdp;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setMdp(final String mdp) {
		this.mdp = mdp;
	}

}
