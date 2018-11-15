package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;

public interface RoleRepository extends CrudRepository<Role, String> {

	@Query("select r from Role r")
	Page<Role> listerRoles(Pageable requete);

}
