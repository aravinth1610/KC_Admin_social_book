package com.book.network.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.book.network.modal.Roles;
import com.book.network.services.AuthMenuServices;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebConfig {
	
	private final AuthMenuServices authMenuServices;
	
	
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                return config;
            }
        }))
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(auth -> {
            	
//            	try {
//            	    this.authMenuServices.getSecurityConfigPermission().forEach(permission -> {
//            	        if (permission.getCanActivate() != null) {
//            	            try {
//            	                String[] authorities = permission.getRoles().stream().map(Roles::getName).toArray(String[]::new);
//
//            	                // Split multiple methods (e.g., "GET,POST")
//            	                Arrays.stream(permission.getPermission().split(",")).map(String::trim).map(HttpMethod::valueOf)  
//            	                        .forEach(method -> auth.requestMatchers(method, permission.getApiEndPoint()).hasAnyAuthority(authorities));
//
//            	            } catch (IllegalArgumentException e) {
//            	                throw new RuntimeException("Invalid HTTP method: " + permission.getPermission(), e);
//            	            }
//            	        } else {
//            	            Arrays.stream(permission.getPermission().split(",")).map(String::trim).map(HttpMethod::valueOf)
//            	                    .forEach(method -> auth.requestMatchers(method, permission.getApiEndPoint()).permitAll());
//            	        }
//            	    });
//            	} catch (Exception e) {
//            	    throw new RuntimeException("Failed to configure authorization", e);
//            	}
//
//            	auth.anyRequest().authenticated();          

//--------------------------------------------------------------------------------------------------------------------				
				auth
						.requestMatchers( "/user/**","/authmenu/**","/realm/role/**").permitAll().anyRequest().authenticated();
//------------------------------------------------------------------------------------------------------------------------				
			})
  		.oauth2ResourceServer(auth -> auth.jwt(token -> token.jwtAuthenticationConverter(new KeyClockAuthenticationConverter())));
        return http.build();
    }
}
