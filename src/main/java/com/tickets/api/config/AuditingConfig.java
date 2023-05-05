package com.tickets.api.config;

import com.tickets.api.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		UserEntity userEntity = Optional.of((UserEntity) authentication.getPrincipal()).orElse(null);
		return Optional.ofNullable(userEntity.getId().toString() + " - " + userEntity.getEmail());
	}
}
