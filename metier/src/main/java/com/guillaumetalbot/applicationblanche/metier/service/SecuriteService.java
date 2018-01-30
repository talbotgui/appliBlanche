package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

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

	void initialiserOuCompleterConfigurationSecurite(Collection<String> clefsRessources, String loginAdmin, String mdpAdmin, String roleAdmin);

	Collection<Ressource> listerRessources();

	Collection<Role> listerRoles();

	Collection<Utilisateur> listerUtilisateurs();

	Collection<UtilisateurAvecRolesEtAutorisations> listerUtilisateursAvecRolesEtAutorisations();

	void reinitialiserMotDePasse(String login);

	void sauvegarderRole(String nomRole);

	Utilisateur sauvegarderUtilisateur(String login, String mdp);

	void supprimerUtilisateur(String login);

	void verifierUtilisateur(String login, String mdp);
}
