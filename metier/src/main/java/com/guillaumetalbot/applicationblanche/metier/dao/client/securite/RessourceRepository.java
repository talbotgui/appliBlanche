package com.guillaumetalbot.applicationblanche.metier.dao.client.securite;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;

/**
 * Il n'est pas possible de créer une ressource depuis l'application. Elles sont liées au code pour protéger ce dernier. Elles sont donc insérées par
 * scripts SQL créés par les développeurs.
 */
public interface RessourceRepository extends CrudRepository<Ressource, String> {

	@Query("select r.clef from Ressource r order by r.clef")
	Collection<String> listerClefsRessources();

	@Query("select r from Ressource r order by r.clef")
	Collection<Ressource> listerRessources();

}
