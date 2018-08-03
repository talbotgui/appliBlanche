package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guillaumetalbot.applicationblanche.metier.dto.UtilisateurAvecRolesEtAutorisations;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

public interface SecuriteService {

	void associerRoleEtRessource(String nomRole, String clefRessource);

	void associerUtilisateurEtRole(String login, String nomRole);

	Utilisateur chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(String login);

	Utilisateur chargerUtilisateurReadOnly(String login);

	void desassocierRoleEtRessource(String nomRole, String clefRessource);

	void desassocierUtilisateurEtRole(String login, String nomRole);

	void deverrouillerUtilisateur(final String login);

	void initialiserOuCompleterConfigurationSecurite(Collection<Ressource> clefRessources, String loginAdmin, String mdpAdmin, String roleAdmin);

	Page<Ressource> listerRessources(Pageable page);

	Collection<Role> listerRoles();

	Collection<Utilisateur> listerUtilisateurs();

	Collection<UtilisateurAvecRolesEtAutorisations> listerUtilisateursAvecRolesEtAutorisations();

	void notifierConnexion(String login, boolean status);

	void reinitialiserMotDePasse(String login);

	void sauvegarderRole(String nomRole);

	Utilisateur sauvegarderUtilisateur(String login, String mdp);

	void supprimerUtilisateur(String login);
}
