package com.guillaumetalbot.applicationblanche.rest.application;

import com.guillaumetalbot.applicationblanche.metier.service.ChiffrementUtil;

public class AccountCredentials {

	/**
	 * Cette méthode permet d'avoir une instance sans chiffrer le mot de passe !
	 *
	 * Le hachage est nécessaire dans le setter pour que Spring hache le mot de passe reçu de la requête.
	 *
	 * Cette méthode n'est à utiliser que dans les tests.
	 *
	 * @param username
	 * @param password
	 */
	public static AccountCredentials creerInstanceSansChiffreLeMotDePassePourUsageDansTests(final String username, final String password) {
		final AccountCredentials cred = new AccountCredentials();
		cred.username = username;
		cred.password = password;
		return cred;
	}

	private String password;

	private String username;

	public AccountCredentials() {
		super();
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(final String password) {
		this.password = ChiffrementUtil.encrypt(password);
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
