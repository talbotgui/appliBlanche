package com.guillaumetalbot.applicationblanche.metier.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Dossier;

public interface DossierRepository extends CrudRepository<Dossier, Long> {

	@Query("select d from Dossier d left join fetch d.client where d.id = :idDossier")
	Dossier chargerDossierAvecClient(@Param("idDossier") Long idDossier);

}
