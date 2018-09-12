package com.guillaumetalbot.applicationblanche.rest.securite.jwt;

import com.guillaumetalbot.applicationblanche.metier.service.ChiffrementUtil;

/**
 * Classe mappant les paramètres à passer au POST "/login" pour récupérer un token.
 */
public class ParametreDeConnexionDto {

	/**
	 * Cette méthode permet d'avoir une instance sans chiffrer le mot de passe !
	 *
	 * Le hachage est nécessaire dans le setter pour que Spring hache le mot de passe reçu de la requête.
	 *
	 * Cette méthode n'est à utiliser que dans les tests.
	 *
	 * @param login
	 * @param mdp
	 */
	public static ParametreDeConnexionDto creerInstanceSansChiffreLeMotDePassePourUsageDansTests(final String login, final String mdp) {
		final ParametreDeConnexionDto cred = new ParametreDeConnexionDto();
		cred.login = login;
		cred.mdp = mdp;
		return cred;
	}

	private String login;

	private String mdp;

	public ParametreDeConnexionDto() {
		super();
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
		this.mdp = ChiffrementUtil.encrypt(mdp);
	}
}
