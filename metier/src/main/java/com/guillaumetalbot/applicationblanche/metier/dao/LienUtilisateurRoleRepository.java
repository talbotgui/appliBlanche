package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.LienUtilisateurRole;

public interface LienUtilisateurRoleRepository extends CrudRepository<LienUtilisateurRole, Long> {

	@Query("select l from LienUtilisateurRole l where l.id.role.nom = :nomRole and l.id.utilisateur.login = :loginUtilisateur")
	LienUtilisateurRole chargerLien(@Param("loginUtilisateur") String loginUtilisateur, @Param("nomRole") String nomRole);
}
