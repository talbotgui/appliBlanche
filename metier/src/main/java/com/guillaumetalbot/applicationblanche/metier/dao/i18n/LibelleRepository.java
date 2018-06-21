package com.guillaumetalbot.applicationblanche.metier.dao.i18n;

import java.util.Collection;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.guillaumetalbot.applicationblanche.metier.entite.i18n.Libelle;

public interface LibelleRepository extends CrudRepository<Libelle, Long> {

	@Query("select l from Libelle l where l.langue = :langue")
	@QueryHints(value = { @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true") })
	Collection<Libelle> listerParLangue(@Param("langue") String langue);

}
