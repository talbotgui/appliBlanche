package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consomation;

public interface ConsomationRepository extends CrudRepository<Consomation, Long> {

	@Query("select c from Consomation c where c.reservation.id = :idReservation order by c.dateCreation")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Consomation> rechercherConsomationsDuneReservation(@Param("idReservation") Long idReservation);

}
