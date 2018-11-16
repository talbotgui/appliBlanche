package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;

public interface RoleRepository extends CrudRepository<Role, String> {

	// Obligation de définir countQuery à cause du "join fetch"
	@Query(value = "select r from Role r left join fetch r.ressourcesAutorisees re order by r.nom, re.clef", //
			countQuery = "select count(r) from Role r order by r.nom")
	Page<Role> listerRoles(Pageable requete);

}
