package com.guillaumetalbot.applicationblanche.metier.dao.facture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.facture.Facture;

public interface FactureRepository extends CrudRepository<Facture, Long> {

	@Query("select max(numero) from Facture")
	Long getMaximumNumeroActuel();

	@Query("select f from Facture f where f.reservation.id = :idReservation")
	Collection<Facture> rechercherFacturesParReservation(@Param("idReservation") Long idReservation);

}
