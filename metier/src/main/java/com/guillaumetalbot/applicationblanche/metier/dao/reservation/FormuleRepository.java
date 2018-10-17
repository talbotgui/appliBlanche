package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;

public interface FormuleRepository extends CrudRepository<Formule, Long> {

	@Query("select f from Formule f order by f.nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Formule> listerFormules();

	@Query("select f from Formule f where f.nom = :nom")
	Optional<Formule> rechercherFormuleManagedParNom(@Param("nom") String nom);

}
