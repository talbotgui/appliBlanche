package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;

public interface ConsommationRepository extends CrudRepository<Consommation, Long> {

	@Query("select c from Consommation c where c.reservation.id = :idReservation order by c.dateCreation")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Consommation> rechercherConsommationsDuneReservation(@Param("idReservation") Long idReservation);

}
