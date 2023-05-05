package com.tickets.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class UserAlreadyExistException extends RuntimeException {
    private final String entity;

    public UserAlreadyExistException(final String entity) {
        this.entity = entity;
    }

}
