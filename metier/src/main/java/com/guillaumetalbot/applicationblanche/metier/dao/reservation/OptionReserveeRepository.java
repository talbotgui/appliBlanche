package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.OptionReservee;

public interface OptionReserveeRepository extends CrudRepository<OptionReservee, Long> {

	@Query("select count(o) from OptionReservee o where o.id.option.id = :idOption")
	Long compterOptionReserveePourCetteOption(@Param("idOption") Long id);

}
