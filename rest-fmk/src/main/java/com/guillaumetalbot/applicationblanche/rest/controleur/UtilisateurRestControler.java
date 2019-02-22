package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.RestException;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;
import com.guillaumetalbot.applicationblanche.rest.dto.UtilisateurAcreerDto;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class UtilisateurRestControler {

	@Autowired
	private SecuriteService securiteService;

	@PutMapping("/v1/utilisateurs/{login}/roles/{nomRole}")
	@ApiOperation(value = "Ajouter ou retirer un rôle à un utilisateur", notes = "")
	public void ajouterRetirerRole(@RequestBody() final Boolean statut, //
			@PathVariable(value = "nomRole") final String nomRole, //
			@PathVariable(value = "login") final String login) {
		if (statut) {
			this.securiteService.associerUtilisateurEtRole(login, nomRole);
		} else {
			this.securiteService.desassocierUtilisateurEtRole(login, nomRole);
		}
	}

	@PostMapping(value = "/v1/utilisateurs/{login}/changeMdp")
	@ApiOperation(value = "Changer le mot de passe d'un utilisateur", notes = "")
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
	@ApiOperation(value = "Charger les données de l'utilisateur connecté", notes = "")
	public Utilisateur chargerUtilisateurMoi(final HttpServletRequest request) {
		return this.securiteService.chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(request.getRemoteUser());
	}

	@PutMapping(value = "/v1/utilisateurs/{login}/deverrouille")
	@ApiOperation(value = "Déverrouiller le compte d'un utilisateur bloqué", notes = "")
	public void deverrouillerUtilisateur(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.deverrouillerUtilisateur(login);
	}

	@GetMapping(value = "/v1/utilisateurs", produces = { "application/json", "application/json;details" })
	@ApiOperation(value = "Lister les utilisateurs", notes = "Structure simple en sortie. Ou structure complète contenant roles et ressources avec l'entête 'Accept' et la valeur '"
			+ RestControlerUtils.MIME_JSON_DETAILS + "'")
	public Collection<?> listerUtilisateur(@RequestHeader("Accept") final String accept) {
		if (RestControlerUtils.MIME_JSON_DETAILS.equals(accept)) {
			return this.securiteService.listerUtilisateursAvecRolesEtAutorisations();
		} else {
			return this.securiteService.listerUtilisateurs();
		}
	}

	@PutMapping(value = "/v1/utilisateurs/{login}/reset")
	@ApiOperation(value = "Réinitialiser le mot de passe d'un utilisateur", notes = "")
	public void resetPassword(@PathVariable(value = "login") final String login, final HttpServletRequest request) {
		this.securiteService.reinitialiserMotDePasse(login);
	}

	@PostMapping(value = "/v1/utilisateurs")
	@ApiOperation(value = "Sauvegarder l'utilisateur", notes = "")
	public void sauvegarderUtilisateur(//
			@RequestBody final UtilisateurAcreerDto utilisateurAcreer) {
		if (StringUtils.isEmpty(utilisateurAcreer.getLogin())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "login");
		}
		if (StringUtils.isEmpty(utilisateurAcreer.getMdp())) {
			throw new RestException(RestException.ERREUR_PARAMETRE_MANQUANT, "mdp");
		}
		// Validation coté WEB car elle est nécessaire à cause d'un problème WEB
		// (au delete avec un . dans le parametre)
		if (utilisateurAcreer.getLogin().contains(".")) {
			throw new RestException(RestException.ERREUR_VALEUR_PARAMETRE, "login", "a-zA-Z0-9", "avec des caractères speciaux");
		}
		this.securiteService.sauvegarderUtilisateur(utilisateurAcreer.getLogin(), utilisateurAcreer.getMdp());
	}

	@DeleteMapping(value = "/v1/utilisateurs/{login}")
	@ApiOperation(value = "Supprimer un utilisateur", notes = "")
	public void supprimerUtilisateur(@PathVariable(value = "login") final String login) {
		this.securiteService.supprimerUtilisateur(login);
	}

}
