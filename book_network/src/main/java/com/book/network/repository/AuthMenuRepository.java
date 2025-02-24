package com.book.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.network.modal.AuthRoutes;

@Repository
public interface AuthMenuRepository extends JpaRepository<AuthRoutes, Long> {

	
	
}
