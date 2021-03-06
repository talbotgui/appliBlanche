package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
public class RoleEtRessourceRestControler {

	@Autowired
	private SecuriteService securiteService;

	@PutMapping("/roles/{nomRole}/ressource/{clefRessource}")
	@ApiOperation(value = "Ajouter ou retirer une ressource à un rôle", notes = "body de type boolean (true pour ajouter)")
	public void ajouterRetirerAutorisation(@RequestBody() final Boolean statut, //
			@PathVariable(value = "nomRole") final String nomRole, //
			@PathVariable(value = "clefRessource") final String clefRessource) {
		if (statut) {
			this.securiteService.associerRoleEtRessource(nomRole, clefRessource);
		} else {
			this.securiteService.desassocierRoleEtRessource(nomRole, clefRessource);
		}
	}

	@GetMapping("/ressources")
	@ApiOperation(value = "Lister les ressources de l'application", notes = "Lecture paginée")
	public Page<Ressource> listerRessource(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber,
			@RequestParam(required = false, value = "triParClef") final Boolean triParClef) {
		final Sort tri = RestControlerUtils.creerTriSiPossible("clef", triParClef);
		final Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber, tri);
		return this.securiteService.listerRessources(page);
	}

	@GetMapping("/roles")
	@ApiOperation(value = "Lister les rôles de l'application", notes = "Lecture paginée")
	public Object listerRoles(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber,
			@RequestParam(required = false, value = "triParNom") final Boolean triParNom) {
		final Sort tri = RestControlerUtils.creerTriSiPossible("nom", triParNom);
		final Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber, tri);
		return this.securiteService.listerRoles(page);
	}

	@PostMapping("/roles")
	@ApiOperation(value = "Sauvegarder nouveau un rôle", notes = "")
	public void sauvegarderRole(@RequestParam(value = "nom") final String nom) {
		this.securiteService.sauvegarderRole(nom);
	}

	@DeleteMapping(value = "/roles/{nom}")
	@ApiOperation(value = "Supprimer un rôle", notes = "")
	public void supprimerRole(@PathVariable(value = "nom") final String nom) {
		this.securiteService.supprimerRole(nom);
	}
}
