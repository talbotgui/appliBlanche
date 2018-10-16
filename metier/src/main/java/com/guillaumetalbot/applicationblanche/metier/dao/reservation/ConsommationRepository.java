package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;

public interface ConsommationRepository extends CrudRepository<Consommation, Long> {

	@Query("select count(c) from Consommation c where c.reservation.id = :idReservation")
	Long compterConsommationDeLaReservation(@Param("idReservation") Long idReservation);

	@Query("select count(c) from Consommation c where c.produit.id = :idProduit")
	Long compterConsommationDuProduit(@Param("idProduit") Long idProduit);

	@Query("select c.reservation.id from Consommation c where c.id = :idConsommation")
	Long getIdReservationByIdConsommation(@Param("idConsommation") Long idConsommation);

	@Query("select c from Consommation c where c.reservation.id = :idReservation order by c.dateCreation")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Consommation> rechercherConsommationsDuneReservation(@Param("idReservation") Long idReservation);

}
