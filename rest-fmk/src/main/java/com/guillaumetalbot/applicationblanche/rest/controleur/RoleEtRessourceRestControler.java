package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

@RestController
public class RoleEtRessourceRestControler {

	@Autowired
	private SecuriteService securiteService;

	@RequestMapping(value = "/v1/ressources", method = GET)
	public Page<Ressource> listerRessource(final Pageable page) {
		return this.securiteService.listerRessources(page);
	}

	@RequestMapping(value = "/v1/roles", method = GET)
	public Collection<Role> listerRole() {
		final Collection<Role> liste = this.securiteService.listerRoles();
		return liste;
	}

	@RequestMapping(value = "/v1/roles", method = POST)
	public void sauvegarderUtilisateur(@RequestParam(value = "nom", required = true) final String nom) {
		this.securiteService.sauvegarderRole(nom);
	}
}