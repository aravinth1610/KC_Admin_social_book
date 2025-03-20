package com.book.network.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "auth_orgconfig",uniqueConstraints = { @UniqueConstraint(columnNames = "orgid") })
public class AuthOrgConfig {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "pk_auth_orgconfig")
	private Long pkAuthOrgConfig;

	@Column (name = "orgid", nullable = false)
	private Long orgId;

	@Column (name = "client_id")
	private String clientId;

	@Column (name = "client_secret")
	private String clientSecret;

}
