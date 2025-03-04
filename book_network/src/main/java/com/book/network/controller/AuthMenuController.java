package com.book.network.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.network.modal.AuthRoutes;
import com.book.network.repositoryDTO.SecurityConfigRepositoryDTO;
import com.book.network.services.AuthMenuServices;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/authmenu")
public class AuthMenuController {

	private final AuthMenuServices serv;

	@PostMapping
	private final List<AuthRoutes> createAuthMenu(@RequestBody List<AuthRoutes> menu) {
		System.out.println("menu"+menu);
		return serv.createAuthRoutes(menu);
	}

	@GetMapping
	public List<AuthRoutes> getAllAuthMenu() {
		return serv.getListOfAuthRoutes();
	}
	

	@GetMapping("/web")
	public Set<SecurityConfigRepositoryDTO> getSecurityWebConfig() {
		return serv.getSecurityConfigPermission();
	}

}
