package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;

/**
 * Il n'est pas possible de créer une ressource depuis l'application. Elles sont liées au code pour protéger ce dernier. Elles sont donc insérées par
 * scripts SQL créés par les développeurs.
 */
public interface RessourceRepository extends Repository<Ressource, String> {

	@Query("select r from Ressource r order by r.clef")
	Collection<Ressource> listerRessources();

}
