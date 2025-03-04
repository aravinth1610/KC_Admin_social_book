package com.book.network.controller;

import java.util.List;
import java.util.Set;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.network.DTO.ClientRoles;
import com.book.network.DTO.User;
import com.book.network.services.UserKcServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class KcUserController {

	private UserKcServices userKcServices;

	@PostMapping(value = "/orgId/{orgId}/user")
	public UserRepresentation createUser(@PathVariable(value="orgId") Long orgId ,@RequestBody User user) {
		System.out.println(user);
		return userKcServices.createUser(orgId, user);
	}

	@GetMapping("/user/{id}")
	private UserRepresentation getUserById(@PathVariable(value = "id") String id) {
		System.out.println(id);
		return userKcServices.getUserById(id);
	}

	@GetMapping("/user")
	private List<UserRepresentation> getUser() {
		return userKcServices.getListofUser();
	}

	@DeleteMapping("/user/{id}")
	private void deleteUser(@PathVariable(value = "id") String id) {
		userKcServices.deleteUser(id);
	}

	@PutMapping("/user/{id}")
	public void updateUser(@PathVariable String id, @RequestBody User user) {
		System.out.println(id+"-"+user);
		userKcServices.updateUser(id, user);
	}
	
	@PostMapping(value = "/user/{id}/role")
	public void assignUserRole(@PathVariable String id, @RequestParam String role) {
		System.out.println(id+"-"+role);
		userKcServices.assignUserRoles(id, role);
	}
	
	@GetMapping("/user/{id}/clientId/{clientId}/role")
	private List<RoleRepresentation> getUser(@PathVariable String id,@PathVariable String clientId) {
		return userKcServices.getListOfUserRoles(id, clientId);
	}
	
}
