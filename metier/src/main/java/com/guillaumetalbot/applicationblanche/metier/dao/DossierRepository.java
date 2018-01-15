package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

public interface DossierRepository extends CrudRepository<Dossier, Long> {

}
