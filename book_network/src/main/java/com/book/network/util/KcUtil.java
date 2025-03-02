package com.book.network.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import com.book.network.services.UserKcServices;

@Component
public class KcUtil {

	private static KcUtil instance;
	private final UserKcServices userKcServices;

	private KcUtil(UserKcServices userKcServices) {
		this.userKcServices = userKcServices;
	}

	public static KcUtil getInstance(UserKcServices userKcServices) {
		if (instance == null) {
			instance = new KcUtil(userKcServices);
		}
		return instance;
	}

	public Set<String> getGrantedAuthority(String sub, String clientId) {
		List<RoleRepresentation> roles = userKcServices.getListOfUserRoles(sub, clientId);
		return roles.stream().map(RoleRepresentation::getName).collect(Collectors.toSet());
	}
}

