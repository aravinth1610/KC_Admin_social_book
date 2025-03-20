package com.book.network.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.customeResponse.ResponseEntityWrapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TokenController {

	@PostMapping("/validate")
	private final ResponseEntityWrapper<?> validate(Authentication authenticationUser, HttpServletResponse response) {
		System.out.println("==============/Valiate");
	
		if (authenticationUser != null && authenticationUser.getName() != null) {
	    	response.addHeader("X-user-X-Id", authenticationUser.getName().toString());
	    }
	    return new ResponseEntityWrapper<>();
	}

	
}
