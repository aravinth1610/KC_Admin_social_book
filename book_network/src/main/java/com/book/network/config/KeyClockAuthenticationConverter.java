package com.book.network.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import com.book.network.services.UserKcServices;

public class KeyClockAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>{
	
	@Autowired
	private UserKcServices userKcServices;
	
	public AbstractAuthenticationToken convert(Jwt jwt) {
		   try {
				return new JwtAuthenticationToken(jwt,
						Stream.concat(new JwtGrantedAuthoritiesConverter().convert(jwt).stream(), resourceRoles(jwt).stream())
								.collect(Collectors.toSet()));
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new RuntimeException("Keycloak token validation failed", e);
	        }
	}

	private Collection<? extends GrantedAuthority> resourceRoles(Jwt jwtToken) {
		System.out.println("--------token");
		System.out.println(getSub(jwtToken)+"-"+getClient(jwtToken));
		Set<String> authorities = getGrantedAuthority(getSub(jwtToken), getClient(jwtToken));
		
		System.out.println(authorities);
		
		Arrays.asList("USER", "ADMIN");
		return //((List<String>) realmAccess.get("roles"))
				Arrays.asList("USER","ADMIN").stream().map(roleName -> roleName)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

	}
	
	private String getSub(Jwt jwtToken) {
	return jwtToken.getSubject();
	}

	private String getClient(Jwt jwtToken) {
		return jwtToken.getClaim("azp"); 
	}
	
	private Set<String> getGrantedAuthority(String sub, String clientId) {
		List<RoleRepresentation> roles = userKcServices.getListOfUserRoles(sub, clientId);
		return roles.stream().map(RoleRepresentation::getName).collect(Collectors.toSet());
	}

}