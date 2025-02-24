package com.book.network.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;


public class KeyClockAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>{

	
	public AbstractAuthenticationToken convert(Jwt jwt) {
		   try {
				return new JwtAuthenticationToken(jwt,
						Stream.concat(new JwtGrantedAuthoritiesConverter().convert(jwt).stream(), resourceRoles(jwt).stream())
								.collect(Collectors.toSet()));
	        } catch (Exception e) {
	            throw new RuntimeException("Keycloak token validation failed", e);
	        }
	}

	private Collection<? extends GrantedAuthority> resourceRoles(Jwt jwtToken) {
		System.out.println("--------token");
	//	Map<String, Object> realmAccess = jwtToken.getClaim("realm_access");
	//	System.out.println(realmAccess);
		Arrays.asList("USER", "ADMIN");
		return //((List<String>) realmAccess.get("roles"))
				Arrays.asList("USER","ADMIN").stream().map(roleName -> roleName)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

	}
	
	private String getSub(Jwt jwtToken) {
	return jwtToken.getSubject();
	}

}