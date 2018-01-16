package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.LienRoleRessource;

public interface LienRoleRessourceRepository extends CrudRepository<LienRoleRessource, Long> {

	@Query("select l from LienRoleRessource l where l.id.role.nom = :nomRole and l.id.ressource.clef = :clefRessource")
	LienRoleRessource chargerLien(@Param("nomRole") String nomRole, @Param("clefRessource") String clefRessource);
}
