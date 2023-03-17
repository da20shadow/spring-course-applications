package com.productivity.user.exceptions;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static UserException userNotFound(Long userId) {
        return new UserException("User not found with id: " + userId);
    }

    public static UserException emailAlreadyExists(String email) {
        return new UserException("Email already exists: " + email);
    }

    public static UserException invalidCredentials() {
        return new UserException("Invalid credentials");
    }

    //TODO: Add other common exceptions related to users here
}

