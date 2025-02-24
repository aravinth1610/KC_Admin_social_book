package com.book.network.controller;

import java.util.List;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.network.DTO.Role;
import com.book.network.services.RoleKcServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KcRoleController {

	private final RoleKcServices serv;
	
	@PostMapping("/realm/role")
	private final void createRole(@RequestBody Role role) {
		System.out.println(role);
		 serv.createRole(role);
	}
	
	@GetMapping("/realm/roles")
	public List<RoleRepresentation> getListOfRoles() {
		return serv.getListOfRole();
	}

	@GetMapping("/realm/role/{roleName}")
	public RoleRepresentation getListOfRoles(@PathVariable(value="roleName") String roleName) {
		System.out.println(roleName);
		return serv.getRole(roleName);
	}
	
	@PutMapping("/realm/role/{roleName}")
	public void updateRole(@PathVariable String roleName, @RequestBody Role role) {
		serv.updateRole(roleName, role);
	}
	
	@DeleteMapping("/realm/role/{roleName}")
	public void deleteRole(@PathVariable String roleName) {
		serv.deleteUser(roleName);
	}
	
}
