package com.book.network.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import com.book.network.DTO.AttributeDTO;
import com.book.network.DTO.Role;
import com.book.network.modal.AuthRoutes;
import com.book.network.repository.AuthMenuRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthMenuServices {

	private final AuthMenuRepository authMenuRepo;

	private final RoleKcServices roleKcServices;

	@Transactional
	public List<AuthRoutes> createAuthRoutes(Set<AuthRoutes> menu) {
		List<AuthRoutes> authRoutes = authMenuRepo.saveAll(new ArrayList<>(menu));

		authRoutes.forEach(route -> {
			if (route.getRoles() == null || route.getRoles().isEmpty()) {
				return;
			}
			
			AttributeDTO attribute = new AttributeDTO(route.getPath(), List.of(String.valueOf(route.getPkAuthRoute())));

			route.getRoles().forEach(role -> {
				role.setAttributes(List.of(attribute));
				roleKcServices.createRole(role);
			});
		});

		return authRoutes;
	}

	public List<AuthRoutes> getListOfAuthRoutes() {
		List<AuthRoutes> authRoutes = authMenuRepo.findAll();
	    List<String> attributeValues = authRoutes.stream()
	            .map(m -> String.valueOf(m.getPkAuthRoute())) // Replace with the actual method to get attribute values
	            .collect(Collectors.toList());
	    Set<RoleRepresentation> roles =	roleKcServices.getRolesByAttributeValues(attributeValues);
	    
	  return authRoutes;
	}

	
	
	private Role mapRole(RoleRepresentation roleRep) {
		Role role = new Role();
		role.setId(roleRep.getId());
		role.setName(roleRep.getName());
		return role;
	}
}
