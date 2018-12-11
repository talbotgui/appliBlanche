package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.MoyenDePaiement;

public interface MoyenDePaiementRepository extends CrudRepository<MoyenDePaiement, Long> {

	@Query("select m from MoyenDePaiement m order by upper(m.nom)")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<MoyenDePaiement> listerMoyensDePaiement();

	@Query("select m from MoyenDePaiement m where m.nom = :nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Optional<MoyenDePaiement> rechercherParNom(@Param("nom") String nom);

}
