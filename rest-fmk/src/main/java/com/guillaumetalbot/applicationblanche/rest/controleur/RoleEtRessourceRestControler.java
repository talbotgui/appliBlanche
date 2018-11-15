package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;

@RestController
@RequestMapping("/v1")
public class RoleEtRessourceRestControler {

	@Autowired
	private SecuriteService securiteService;

	@GetMapping(value = "/ressources")
	public Page<Ressource> listerRessource(final Pageable page) {
		return this.securiteService.listerRessources(page);
	}

	@GetMapping(value = "/roles")
	public Object listerRoles(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber,
			@RequestParam(required = false, value = "triParNom") final Boolean triParNom) {
		final Sort tri = RestControlerUtils.creerTriSiPossible("nom", triParNom);
		final Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber, tri);
		return this.securiteService.listerRoles(page);
	}

	@PostMapping(value = "/roles")
	public void sauvegarderRole(@RequestParam(value = "nom") final String nom) {
		this.securiteService.sauvegarderRole(nom);
	}

	@PostMapping(value = "/v1/roles")
	public void sauvegarderUtilisateur(@RequestParam(value = "nom", required = true) final String nom) {
		this.securiteService.sauvegarderRole(nom);
	}
}
