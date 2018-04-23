package com.guillaumetalbot.applicationblanche.metier.dao.securite;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;

public interface RoleRepository extends CrudRepository<Role, String> {

	@Query("select r from Role r order by r.nom")
	Collection<Role> listerRoles();

}
