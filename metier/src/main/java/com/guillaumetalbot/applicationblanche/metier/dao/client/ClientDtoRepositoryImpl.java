package com.guillaumetalbot.applicationblanche.metier.dao.client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;

@Repository
public class ClientDtoRepositoryImpl implements ClientDtoRepository {

	@Autowired
	private EntityManager em;

	/**
	 * Recherche des clients.
	 *
	 * Cette méthode n'est pas implémentée dans un Repository de Spring Data car le tri ne fonctionne pas dans une méthode avec @Query retournant un
	 * DTO avec une pagination :(
	 *
	 * @param requete
	 *            La requête de pagination et tri
	 * @return La liste paginée et triée des clients sous forme de DTO
	 */
	@Override
	public Page<ClientDto> listerClientsDto(final Pageable requete) {

		// Requêtes
		final String hqlCount = "select count(c.id) from Client c";
		String hql = "select new com.guillaumetalbot.applicationblanche.metier.dto.ClientDto"
				+ "(c.id, c.nom, adr.ville, count(distinct dem), count(distinct dos), max(dos.dateCreation))"//
				+ " from Client c left join c.dossiers dos left join dos.demandes dem left join c.adresse adr"//
				+ " group by c.id, c.nom, c.adresse.ville";

		// Ajout du tri (uniquement possible sur le nom du client
		if (requete.getSort().isSorted()) {
			final Order tri = requete.getSort().iterator().next();
			hql += " order by c.nom " + tri.getDirection().name();
		}

		// récupération des données
		final TypedQuery<ClientDto> q = this.em.createQuery(hql, ClientDto.class);
		q.setFirstResult(requete.getPageNumber() * requete.getPageSize());
		q.setMaxResults(requete.getPageSize());
		final List<ClientDto> results = q.getResultList();

		// récupération du nombre total d'éléments
		final TypedQuery<Number> qCount = this.em.createQuery(hqlCount, Number.class);
		final long totalResultsCount = qCount.getSingleResult().longValue();

		// renvoi d'une page de résultats
		return new PageImpl<>(results, requete, totalResultsCount);
	}

}
