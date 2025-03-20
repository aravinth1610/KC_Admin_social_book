package com.book.network.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.network.modal.AuthOrgConfig;

@Repository
public interface AuthOrgConfigRepository extends JpaRepository<AuthOrgConfig, Long> {

	Optional<AuthOrgConfig> findByOrgId(Long orgId);

}
