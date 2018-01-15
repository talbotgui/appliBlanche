package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

	@Query("select u from Utilisateur u left join fetch u.roles r left join fetch r.ressourcesAutorisees where u.login = :login")
	Utilisateur chargerUtilisateurAvecRolesEtRessourcesAutorisees(@Param("login") String login);

	@Query("select u from Utilisateur u order by u.login")
	Collection<Utilisateur> listerUtilisateur();

}
