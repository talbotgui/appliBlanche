package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

@RestController
public class UtilisateurRestControler {

	private static final String LOGOUT_REST = "/dologout";
	private static final String SESSION_KEY_USER_LOGIN = "USER_LOGIN";

	@Autowired
	private SecuriteService securiteService;

	@RequestMapping(value = "/utilisateur/{login}/changeMdp", method = POST)
	public void changerMotDePasseUtilisateur(//
			@PathVariable(value = "login") final String login, //
			@RequestParam(value = "mdp", required = false) final String mdp) {

		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		if (u == null) {
			throw new RestException(RestException.OBJET_INEXISTANTE);
		}
		this.securiteService.sauvegarderUtilisateur(u.getLogin(), mdp);
	}

	@RequestMapping(value = "/utilisateur/moi", method = GET)
	public Utilisateur chargerUtilisateurMoi(final HttpServletRequest request) {
		final String login = (String) request.getSession().getAttribute(SESSION_KEY_USER_LOGIN);
		return this.securiteService.chargerUtilisateurReadOnly(login);
	}

	@RequestMapping(value = "/dologin", method = POST)
	public void connecter(//
			@RequestParam(value = "login") final String login, //
			@RequestParam(value = "mdp") final String mdp, //
			final HttpServletRequest request, //
			final HttpServletResponse response) {
		this.securiteService.verifierUtilisateur(login, mdp);
		request.getSession().setAttribute(SESSION_KEY_USER_LOGIN, login);
	}

	@RequestMapping(value = LOGOUT_REST, method = GET)
	public void deconnecter(final HttpServletRequest request, final HttpServletResponse response) {
		request.getSession().removeAttribute("USER_LOGIN");
		request.getSession().invalidate();
	}

	@RequestMapping(value = "/utilisateur/{login}/deverrouille", method = PUT)
	public void deverrouillerUtilisateur(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.deverrouillerUtilisateur(login);
	}

	@RequestMapping(value = "/utilisateur", method = GET)
	public Collection<Utilisateur> listerUtilisateur() {
		return this.securiteService.listerUtilisateurs();
	}

	@RequestMapping(value = "/utilisateur/{login}/reset", method = PUT)
	public void resetPassword(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.reinitialiserMotDePasse(login);
	}

	@RequestMapping(value = "/utilisateur", method = POST)
	public void sauvegarderUtilisateur(//
			@RequestParam(value = "login") final String login, //
			@RequestParam(value = "mdp", required = false) final String mdp) {
		// Validation coté WEB car elle est nécessaire à cause d'un problème WEB
		// (au delete avec un . dans le parametre)
		if (login.contains(".")) {
			throw new RestException(RestException.ERREUR_VALEUR_PARAMETRE, "login", "a-zA-Z0-9", "avec des caractères speciaux");
		}
		this.securiteService.sauvegarderUtilisateur(login, mdp);
	}

	@RequestMapping(value = "/utilisateur/{login}", method = DELETE)
	public void supprimerUtilisateur(@PathVariable(value = "login") final String login) {
		this.securiteService.supprimerUtilisateur(login);
	}
}
