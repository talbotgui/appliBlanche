package com.guillaumetalbot.applicationblanche.metier.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.Ressource;

public interface RessourceRepository extends CrudRepository<Ressource, String> {

	@Query("select r from Ressource r order by r.clef")
	Collection<Ressource> listerRessources();

}
