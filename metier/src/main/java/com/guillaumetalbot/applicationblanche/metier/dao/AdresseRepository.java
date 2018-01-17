package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Adresse;

public interface AdresseRepository extends CrudRepository<Adresse, Long> {

}