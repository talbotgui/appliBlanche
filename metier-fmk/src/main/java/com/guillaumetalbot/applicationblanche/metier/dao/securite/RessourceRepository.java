package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;

/**
 * Il n'est pas possible de créer une ressource depuis l'application. Elles sont liées au code pour protéger ce dernier. Elles sont donc insérées par
 * scripts SQL créés par les développeurs.
 */
public interface RessourceRepository extends CrudRepository<Ressource, String> {

	@Query("select r from Ressource r order by r.clef")
	Page<Ressource> listerRessources(Pageable page);

	@Query("select r.chemin from Ressource r group by r.chemin having count(r) > 1")
	List<String> listerRessourcesAuCheminSimilaire();

	@Query("select r.clef from Ressource r group by r.clef having count(r) > 1")
	List<String> listerRessourcesAuClefSimilaire();

}
