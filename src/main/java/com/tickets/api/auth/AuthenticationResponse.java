package com.tickets.api.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String id;
}
