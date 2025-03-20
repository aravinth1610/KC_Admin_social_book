package com.book.network.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.network.modal.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

	@Query("SELECT r.pkRoleId FROM Roles r WHERE r.name=:roleName")
	List<Long> existsByRoleName(@Param(value = "roleName") List<String> roleName);
	
}
