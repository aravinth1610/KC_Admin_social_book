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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.HttpMethod;

@Configuration
public class WebConfig {
	
	
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
//				this.roleBase.getPermissions().forEach(permission -> {
//				 try {
//				auth.requestMatchers(HttpMethod.valueOf(permission.getMethod()), permission.getEndpoints())
//						.hasAnyAuthority(permission.getRoles().split(","));
//				
//				 } catch (Exception e) {
//                        throw new RuntimeException("Failed to configure authorization", e);
//                    }
//			});
//			 auth.requestMatchers(AppConstant.PUBLIC_URLS).permitAll().anyRequest().authenticated();
//--------------------------------------------------------------------------------------------------------------------				
				auth
						.requestMatchers( "/user","/authmenu/**","/realm/role/**").permitAll().anyRequest().authenticated();
//------------------------------------------------------------------------------------------------------------------------				
			})
   
		.oauth2ResourceServer(auth -> auth.jwt(token -> token.jwtAuthenticationConverter(new KeyClockAuthenticationConverter())));
        return http.build();
    }
}
