package com.guillaumetalbot.applicationblanche.metier.dao.reservation;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Produit;

public interface ProduitRepository extends CrudRepository<Produit, Long> {

	@Query("select p from Produit p order by upper(p.nom)")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Produit> listerProduits();

	@Query("select p from Produit p where p.nom = :nom")
	Optional<Produit> rechercherProduitManagedParNom(@Param("nom") String nom);
}
