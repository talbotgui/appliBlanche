package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.LienRoleRessource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;

public interface LienRoleRessourceRepository extends CrudRepository<LienRoleRessource, Long> {

	@Query("select l from LienRoleRessource l where l.id.role.nom = :nomRole and l.id.ressource.clef = :clefRessource")
	LienRoleRessource chargerLien(@Param("nomRole") String nomRole, @Param("clefRessource") String clefRessource);

	/**
	 * Recherche de LienRoleRessource pour toutes les ressources existantes qui ne sont pas encore liées au role :nomRole
	 *
	 * @param nomRole
	 * @return
	 */
	@Query(value = "select new LienRoleRessource(ro, re) from  Ressource re, Role ro"//
			+ " where ro.nom = :nomRole"//
			+ " and re.clef not in (select li.id.ressource.clef from LienRoleRessource li where li.id.role.nom = :nomRole)")
	Collection<LienRoleRessource> listerLiensInexistantsAvecToutesLesRessources(@Param("nomRole") String nomRole);

	@Query("delete from LienRoleRessource where id.ressource in :ressources")
	@Modifying
	void supprimerParRessources(@Param("ressources") Collection<Ressource> ressources);

	@Query("delete from LienRoleRessource where id.role.nom = :nomRole")
	@Modifying
	void supprimerParRole(@Param("nomRole") String nomRole);
}
