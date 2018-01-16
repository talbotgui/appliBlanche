package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.Collection;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

public interface SecuriteService {

	void associerRoleEtRessource(String nomRole, String clefRessource);

	void associerUtilisateurEtRole(String login, String nomRole);

	Utilisateur chargerUtilisateurReadOnly(String login);

	Utilisateur chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(String login);

	void desassocierRoleEtRessource(String nomRole, String clefRessource);

	void desassocierUtilisateurEtRole(String login, String nomRole);

	void deverrouillerUtilisateur(final String login);

	Collection<Ressource> listerRessources();

	Collection<Role> listerRoles();

	Collection<Utilisateur> listerUtilisateurs();

	void reinitialiserMotDePasse(String login);

	void sauvegarderRole(String nomRole);

	void sauvegarderUtilisateur(String login, String mdp);

	void supprimerUtilisateur(String login);

	void verifierUtilisateur(String login, String mdp);
}
