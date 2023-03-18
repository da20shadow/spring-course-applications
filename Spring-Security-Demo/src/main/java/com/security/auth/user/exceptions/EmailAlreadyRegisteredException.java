package com.security.auth.user.exceptions;

public class EmailAlreadyRegisteredException extends UserException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
