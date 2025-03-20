package com.book.network.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.book.network.DTO.ClientRoles;
import com.book.network.modal.Roles;
import com.book.network.services.ClientRoleKcServices;
import com.unicore.customeResponse.ResponseEntityWrapper;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KcClientRoleController {

	private final ClientRoleKcServices clientRoleservices;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/client/roles")
	public final ResponseEntityWrapper<?> createClientRoles(@RequestBody Set<ClientRoles> clientRoles) {
		clientRoleservices.createClientRole(clientRoles);
		return new ResponseEntityWrapper<>();
	}

	@GetMapping(value = "/client/roles/{clientId}")
	public final ResponseEntityWrapper<?> getClientRoles(@PathVariable(value = "clientId") String  clientId) {
		return new ResponseEntityWrapper<>(clientRoleservices.getAllRoleByClientId(clientId));
	}
	
	@GetMapping(value = "/client/roles/over-all/{clientId}")
	public final ResponseEntityWrapper<?> getOverAllClientRoles(@PathVariable(value = "clientId") String  clientId) {
		return new ResponseEntityWrapper<>(clientRoleservices.getOverAllRolesForClientId(clientId));
	}


	@PutMapping("/client/roles/{clientId}/{roleName}")
	private final ResponseEntityWrapper<?> updateClientRole(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "roleName") String roleName, @RequestBody Roles role) {
		clientRoleservices.updateRoleByClientId(clientId, roleName, role);
		return new ResponseEntityWrapper<>();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/client/roles/{clientId}/{roleName}")
	public final ResponseEntityWrapper<?> deleteClientRole(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "roleName") String roleName) {
		clientRoleservices.deleteRoleByClientId(clientId, roleName);
		return new ResponseEntityWrapper<>();
	}

	
}
