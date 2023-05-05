package com.tickets.api.repository;

import com.tickets.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);
    UserEntity findByEmailAndTenantId(String email, String tenantId);
}
