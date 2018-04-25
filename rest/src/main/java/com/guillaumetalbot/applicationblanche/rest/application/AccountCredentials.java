package com.guillaumetalbot.applicationblanche.rest.application;

public class AccountCredentials {

	private String password;

	private String username;

	public AccountCredentials() {
		super();
	}

	public AccountCredentials(final String password, final String username) {
		super();
		this.password = password;
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
