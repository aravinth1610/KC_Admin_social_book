package com.book.network.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import com.book.network.modal.AuthRoutes;
import com.book.network.repository.AuthMenuRepository;
import com.book.network.repository.RolesRepository;
import com.book.network.repositoryDTO.SecurityConfigRepositoryDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthMenuServices {

	private final AuthMenuRepository authMenuRepo;

	private final RoleKcServices roleKcServices;
	
	private final RolesRepository roleRepository;

	@Transactional
	public List<AuthRoutes> createAuthRoutes(Set<AuthRoutes> menu) {
		Set<String> existingRoleNames = roleKcServices.getListOfRole().stream().map(RoleRepresentation::getName)
				.collect(Collectors.toSet());

		menu.forEach(route -> {
			if (route.getRoles() != null) { // Ensure route is not null
				processAllRoles(route, existingRoleNames);
			}
		});

		return authMenuRepo.saveAll(menu.stream().filter(Objects::nonNull).toList()); // Filter out null values
	}


	public List<AuthRoutes> getListOfAuthRoutes() {
		List<AuthRoutes> authRoutes = authMenuRepo.findAll();
		return authRoutes;
	}
	
	public Set<SecurityConfigRepositoryDTO> getSecurityConfigPermission(){
		return authMenuRepo.getDataVal();
	}
	
	
	private void processAllRoles(AuthRoutes route, Set<String> existingRoleNames) {
	    // Process roles for current route and all children in one pass
	    Stream.concat(
	            Stream.of(route), // Current route
	            route.getChildrenAuthRoute() != null ? route.getChildrenAuthRoute().stream() : Stream.empty() // Child routes
	    )
	    .flatMap(r -> r.getRoles().stream()) // Get all roles
	    .filter(role -> !existingRoleNames.contains(role.getName())) // Filter out existing roles
	    .forEach(roleKcServices::createRole); // Create roles
	}

}
