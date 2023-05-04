package com.tickets.api.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    String email;
    String password;
}
