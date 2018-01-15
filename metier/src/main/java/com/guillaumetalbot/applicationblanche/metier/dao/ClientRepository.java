package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

	@Query("select c from Client c left join fetch c.adresse where c.id = :idClient")
	Client chargerClientAvecAdresse(@Param("idClient") Long idClient);

}
