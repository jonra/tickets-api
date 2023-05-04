package com.tickets.api.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AuthenticationRequest {
    String email;
    String password;
}
