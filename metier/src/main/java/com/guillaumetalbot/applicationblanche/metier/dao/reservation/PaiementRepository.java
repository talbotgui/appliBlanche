package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;

public interface PaiementRepository extends CrudRepository<Paiement, Long> {

	@Query("select count(p) from Paiement p where p.moyenDePaiement.id=:id")
	Long compterPaiementPourCeMoyenDePaiement(@Param("id") Long id);

	@Query("select p.reservation.id from Paiement p where p.id = :idPaiement")
	Long getIdReservationByIdPaiement(@Param("idPaiement") Long idPaiement);

	@Query("select p from Paiement p where p.reservation.id = :idReservation order by p.dateCreation")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Paiement> rechercherPaiementsDuneReservation(@Param("idReservation") Long idReservation);

}
