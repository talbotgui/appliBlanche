package com.guillaumetalbot.applicationblanche.metier.dao.client;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;
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

	@Query("select new com.guillaumetalbot.applicationblanche.metier.dto.ClientDto"
			+ " (c.id, c.nom, adr.ville, count(distinct dem), count(distinct dos), max(dos.dateCreation))"//
			+ " from Client c left join c.dossiers dos left join dos.demandes dem left join c.adresse adr"//
			+ " group by c.id, c.nom, c.adresse.ville"//
			+ " order by c.nom")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Page<ClientDto> listerClientsDto(Pageable requete);

}
