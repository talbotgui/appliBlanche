package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	@Query("select count(r) from Reservation r where r.dateDebut < :dateFin and :dateDebut < r.dateFin and r.chambre.id = :chambreId and (r.id <> :reservationId or :reservationId is null)")
	Long compterReservationsDeLaChambre(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin,
			@Param("chambreId") Long chambreId, @Param("reservationId") Long reservationId);

	@Query("select count(r) from Reservation r where r.chambre.id = :chambreId")
	Long compterReservationsDeLaChambre(@Param("chambreId") Long chambreId);

	@Query("select r from Reservation r where r.dateDebut<:dateFin and r.dateFin>:dateDebut order by r.dateDebut, r.chambre.nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Reservation> rechercherReservations(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}
