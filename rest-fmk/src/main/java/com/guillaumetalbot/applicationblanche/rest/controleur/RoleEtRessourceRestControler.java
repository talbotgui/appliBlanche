package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

@RestController
public class RoleEtRessourceRestControler {

	@Autowired
	private SecuriteService securiteService;

	@GetMapping(value = "/v1/ressources")
	public Page<Ressource> listerRessource(final Pageable page) {
		return this.securiteService.listerRessources(page);
	}

	@GetMapping(value = "/v1/roles")
	public Collection<Role> listerRole() {
		final Collection<Role> liste = this.securiteService.listerRoles();
		return liste;
	}

	@PostMapping(value = "/v1/roles")
	public void sauvegarderUtilisateur(@RequestParam(value = "nom", required = true) final String nom) {
		this.securiteService.sauvegarderRole(nom);
	}
}
