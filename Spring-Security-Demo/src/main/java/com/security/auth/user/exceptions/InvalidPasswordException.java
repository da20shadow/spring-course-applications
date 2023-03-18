package com.security.auth.user.exceptions;

public class InvalidPasswordException extends UserException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
