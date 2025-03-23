package com.book.network.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.book.network.keycloakConfig.KeycloakSecurityUtil;
import com.book.network.securityException.CustomAccessDeniedHandler;
import com.book.network.securityException.CustomAuthenticationEntryPoint;
import com.book.network.services.RoleServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebConfig {

	private final DynamicAuthorizationManager authorizationManager;

	private final RoleServices roleServices;

	private final KeycloakSecurityUtil keycloakUtil;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	
		      .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
				}))
		      
		      .csrf((csrf) -> csrf.disable()).authorizeHttpRequests(auth -> {

//Dynamic Request get from Database for ever Request it will get from database
					auth.anyRequest().access(authorizationManager);

//--------------------------------------------------------------------------------------------------------------------				
//			auth
//						.requestMatchers( "/books/**","/user/**","/authmenu/**","/realm/role/**").permitAll().anyRequest().authenticated();
//------------------------------------------------------------------------------------------------------------------------				
				})
				.oauth2ResourceServer(auth -> auth.jwt(token -> token
						.jwtAuthenticationConverter(new KeyClockAuthenticationConverter(roleServices, keycloakUtil))))

				.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())
						.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
				
				.formLogin(formLogin -> formLogin.disable()).httpBasic(httpBasic -> httpBasic.disable());

		return http.build();
	}
}
