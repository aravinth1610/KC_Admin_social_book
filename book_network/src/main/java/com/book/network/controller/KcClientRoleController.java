package com.book.network.controller;

import java.util.List;
import java.util.Set;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.network.DTO.ClientRoles;
import com.book.network.DTO.User;
import com.book.network.modal.Roles;
import com.book.network.services.ClientRoleKcServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KcClientRoleController {

	private final ClientRoleKcServices clientRoleservices;
	
	@PostMapping(value = "/client/roles")
	public void createClientRoles(@RequestBody Set<ClientRoles> clientRoles) {
		clientRoleservices.createClientRole(clientRoles);
	}

	@GetMapping(value = "/client/roles/{clientId}")
	public void getClientRoles(@PathVariable(value = "clientId") String  clientId) {
		clientRoleservices.getAllRoleByClientId(clientId);
	}
	
	@GetMapping(value = "/client/roles/over-all/{clientId}")
	public Set<String> getOverAllClientRoles(@PathVariable(value = "clientId") String  clientId) {
		return clientRoleservices.getOverAllRolesForClientId(clientId);
	}


	@PutMapping("/client/roles/{clientId}/{roleName}")
	private void updateClientRole(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "roleName") String roleName, @RequestBody Roles role) {
		clientRoleservices.updateRoleByClientId(clientId, roleName, role);
	}

	@DeleteMapping("/client/roles/{clientId}/{roleName}")
	public void deleteClientRole(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "roleName") String roleName) {
		clientRoleservices.deleteRoleByClientId(clientId, roleName);
	}

	
}
