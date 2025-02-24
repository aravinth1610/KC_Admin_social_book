package com.book.network.keycloakConfig;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KeycloakSecurityUtil {

	Keycloak keycloak;

	@Value("${server-url}")
	private String serverUrl;

	@Value("${realm}")
	private String realm;

	@Value("${client-id}")
	private String clientId;

	@Value("${client-secret}")
	private String clientSecret;

	@Bean
	public Keycloak getKeycloakInstance() {
		System.out.println("KC INIT");
		if (keycloak == null) {
			System.out.println("--------------");
			keycloak = KeycloakBuilder.builder().serverUrl(serverUrl).grantType(OAuth2Constants.CLIENT_CREDENTIALS)
					.realm(realm).clientId(clientId).clientSecret(clientSecret).build();
		}
		return keycloak;
	}
}
