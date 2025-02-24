package com.book.network.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@RestController
public class NetworkController {


	@GetMapping("/books")
	public List<Object> getData(Authentication  authenticationUser) { //
		System.out.println("----"+authenticationUser.getName()+"--"+authenticationUser.getPrincipal().toString()+"--"+authenticationUser.getAuthorities().toString());
		return Arrays.asList(Map.of("Name", "Ram", "Age", 12, "Books", Arrays.asList("Jemmy", "Makers")));
	}
}
