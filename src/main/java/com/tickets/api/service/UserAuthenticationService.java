package com.tickets.api.service;

import com.tickets.api.auth.AuthenticationRequest;
import com.tickets.api.auth.AuthenticationResponse;
import com.tickets.api.auth.RegisterRequest;
import com.tickets.api.config.security.JwtService;
import com.tickets.api.entity.UserEntity;
import com.tickets.api.enums.Role;
import com.tickets.api.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAuthenticationService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, String tenantId) {

        UserEntity userEntity = userService.findByEmail(request.getEmail());
        if( userEntity !=null) {
            throw new UserAlreadyExistException("User already exists");
        } else {
            Set<Role> roles = null;
            if (request.isOrganiser()) {
                roles = Set.of(Role.ORGANISER);
            } else {
                roles = Set.of(Role.USER);
            }

            UserEntity entity = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(roles)
                    .tenantId(tenantId)
                    .build();
            UserEntity save = userService.save(entity);
            String jwtToken = jwtService.generateToken(entity);
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .email(save.getEmail())
                    .id(save.getId().toString())
                    .firstName(save.getFirstName())
                    .lastName(save.getLastName())
                    .build();

            return authenticationResponse;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, String tenantId) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() + "|" + tenantId, request.getPassword())
        );

        UserEntity userEntity = userService.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(userEntity);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(userEntity.getEmail())
                .id(userEntity.getId().toString())
                .build();

        return authenticationResponse;
    }
}
