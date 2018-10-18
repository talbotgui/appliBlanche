package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;

public interface OptionRepository extends CrudRepository<Option, Long> {

	@Query("select o from Option o order by upper(o.nom)")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Option> listerOptions();

	@Query("select o from Option o where o.nom = :nom")
	Optional<Option> rechercherOptionManagedParNom(@Param("nom") String nom);

}
