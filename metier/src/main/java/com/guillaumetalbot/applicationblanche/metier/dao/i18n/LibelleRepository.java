package com.guillaumetalbot.applicationblanche.metier.dao.i18n;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.i18n.Libelle;

public interface LibelleRepository extends CrudRepository<Libelle, Long> {

	@Query("select l from Libelle l where l.langue = :langue")
	Collection<Libelle> listerParLangue(@Param("langue") String langue);

}
