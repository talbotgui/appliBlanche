package com.guillaumetalbot.applicationblanche.metier.dao.client;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

	@Query("select c from Client c left join fetch c.adresse where c.id = :idClient")
	Client chargerClientAvecAdresse(@Param("idClient") Long idClient);

	@Query("select c from Client c left join fetch c.dossiers left join fetch c.adresse where c.id = :idClient")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Client chargerClientAvecAdresseEtDossiersReadonly(@Param("idClient") Long idClient);

	@Query("select c from Client c where c.id = :idClient")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Client chargerClientReadonly(@Param("idClient") Long idClient);

	@Query("select c from Client c order by c.nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Client> listerClients();

}
