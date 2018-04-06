package com.guillaumetalbot.applicationblanche.metier.dao.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Demande;

public interface DemandeRepository extends CrudRepository<Demande, Long> {

	@Query("select d from Demande d left join fetch d.dossier where d.id = :idDemande")
	Demande chargerDemandeAvecDossier(@Param("idDemande") Long idDemande);

}
