package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Chambre;

public interface ChambreRepository extends CrudRepository<Chambre, Long> {

	@Query("select c from Chambre c order by upper(c.nom)")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Chambre> listerChambres();

	@Query("select c from Chambre c where c.nom = :nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Optional<Chambre> rechercherChambreParNom(@Param("nom") String nom);

}
