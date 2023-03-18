package com.security.auth.user.constants;

public class UserMessages {

    public static class ErrorMessages {
        public static final String LOGIN_BAD_CREDENTIALS = "Bad credentials.";
        public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered.";
        public static final String REGISTRATION_ERROR = "Error occurred while registering user.";
        public static final String USER_NOT_FOUND = "User not found.";
        public static final String INVALID_PASSWORD = "Invalid password.";
        public static final String UPDATE_ERROR = "Error occurred while updating user.";
        public static final String NOTHING_TO_UPDATE_ERROR = "Nothing to update.";
        public static final String DELETE_ERROR = "Error occurred while deleting user.";

    }

    public static class SuccessMessages {
        public static final String LOGIN_SUCCESS = "Successfully logged in.";
        public static final String REGISTRATION_SUCCESS = "Registration successful.";
        public static final String DELETE_SUCCESS = "User deleted successfully.";
        public static final String UPDATE_SUCCESS = "Profile updated successfully.";
    }

}
