package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, String> {

	@Query("select u from Utilisateur u left join fetch u.roles r left join fetch r.ressourcesAutorisees where u.login = :login")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Utilisateur chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(@Param("login") String login);

	@Query("select u from Utilisateur u where u.login = :login")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Utilisateur chargerUtilisateurReadOnly(@Param("login") String login);

	@Query("select u from Utilisateur u order by u.login")
	Collection<Utilisateur> listerUtilisateur();

}
