package com.book.network.config;

import java.util.Set;
import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.book.network.modal.Roles;
import com.book.network.repositoryDTO.SecurityConfigRepositoryDTO;
import com.book.network.services.AuthMenuServices;
 
@Component
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

	private final AuthMenuServices authMenuServices;

	    public DynamicAuthorizationManager(AuthMenuServices authMenuServices) {
	        this.authMenuServices = authMenuServices;
	    }
	
	    @Override
	    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
	        Set<SecurityConfigRepositoryDTO> permissions = authMenuServices.getSecurityConfigPermission();

	        return permissions.stream()
	                .filter(permission -> new AntPathRequestMatcher(permission.getApiEndPoint(), permission.getPermission()).matches(context.getRequest()))
	                .findFirst()
	                .map(permission -> {
	                    if (permission.getCanActivate() == null) {
	                        return new AuthorizationDecision(true); // Permit all
	                    }
	                    return new AuthorizationDecision(authentication.get().getAuthorities().stream()
	                            .anyMatch(grantedAuthority -> permission.getRoles().stream()
	                                    .map(Roles::getName)
	                                    .anyMatch(role -> grantedAuthority.getAuthority().equals(role))));
	                })
	                .orElse(new AuthorizationDecision(false));
	    }

}
