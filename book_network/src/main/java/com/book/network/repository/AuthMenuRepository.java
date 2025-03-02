package com.book.network.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.book.network.modal.AuthRoutes;
import com.book.network.repositoryDTO.SecurityConfigRepositoryDTO;

@Repository
public interface AuthMenuRepository extends JpaRepository<AuthRoutes, Long> {

	@Query("SELECT p.pkAuthRoute AS pkAuthRoute,p.apiEndPoint AS apiEndPoint, p.permission AS permission,p.canActivate AS canActivate,r AS roles FROM AuthRoutes p LEFT JOIN p.roles r")
	Set<SecurityConfigRepositoryDTO> getDataVal();
	
}
