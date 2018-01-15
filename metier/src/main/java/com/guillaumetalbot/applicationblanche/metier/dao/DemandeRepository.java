package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;

public interface DemandeRepository extends CrudRepository<Demande, Long> {

}
