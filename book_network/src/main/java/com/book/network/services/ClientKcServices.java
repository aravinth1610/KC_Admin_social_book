package com.book.network.services;

import java.util.Optional;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientKcServices {

	@Value("${realm}")
	private String realm;

	private Keycloak keycloak;

	public ClientKcServices(Keycloak keycloak) {
		super();
		this.keycloak = keycloak;
	}

	public Optional<ClientRepresentation> getClientId(String clientId) {
		return getClientResource().findByClientId(clientId).stream().findFirst();
	}

	private ClientsResource getClientResource() {
		return keycloak.realm(this.realm).clients();
	}

}
