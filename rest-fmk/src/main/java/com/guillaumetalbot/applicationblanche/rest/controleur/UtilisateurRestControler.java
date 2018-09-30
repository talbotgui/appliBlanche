package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;

@RestController
@CrossOrigin
public class UtilisateurRestControler {

	@Autowired
	private SecuriteService securiteService;

	@PostMapping(value = "/v1/utilisateurs/{login}/changeMdp")
	public void changerMotDePasseUtilisateur(//
			@PathVariable(value = "login") final String login, //
			@RequestParam(value = "mdp", required = false) final String mdp) {

		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		if (u == null) {
			throw new RestException(RestException.OBJET_INEXISTANT);
		}
		this.securiteService.sauvegarderUtilisateur(u.getLogin(), mdp);
	}

	@GetMapping(value = "/v1/utilisateurs/moi")
	public Utilisateur chargerUtilisateurMoi(final HttpServletRequest request) {
		return this.securiteService.chargerUtilisateurReadOnly(request.getRemoteUser());
	}

	@PutMapping(value = "/v1/utilisateurs/{login}/deverrouille")
	public void deverrouillerUtilisateur(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.deverrouillerUtilisateur(login);
	}

	@GetMapping(value = "/v1/utilisateurs", produces = { "application/json", "application/json;details" })
	public Collection<?> listerUtilisateur(@RequestHeader("Accept") final String accept) {
		if (RestControlerUtils.MIME_JSON_DETAILS.equals(accept)) {
			return this.securiteService.listerUtilisateursAvecRolesEtAutorisations();
		} else {
			return this.securiteService.listerUtilisateurs();
		}
	}

	@PutMapping(value = "/v1/utilisateurs/{login}/reset")
	public void resetPassword(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.reinitialiserMotDePasse(login);
	}

	@PostMapping(value = "/v1/utilisateurs")
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

	@DeleteMapping(value = "/v1/utilisateurs/{login}")
	public void supprimerUtilisateur(@PathVariable(value = "login") final String login) {
		this.securiteService.supprimerUtilisateur(login);
	}

}
