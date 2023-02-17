package shop.constants;

public interface Messages {

    //USER
    String USER_CREATED = "Successfully registered!";
    String USER_UPDATED = "Successfully updated!";
    String USER_DELETED = "Successfully deleted user!";

    String USERNAME_TAKEN = "Username already taken";
    String USERNAME_REQUIRED = "Username can not be empty!";
    String USERNAME_INVALID_LENGTH = "Username must be between 3 and 20 characters long!";
    String USER_NOT_FOUND = "User not found!";
    String EMAIL_TAKEN = "Email already taken";
    String EMAIL_INVALID = "Invalid email format!";
    String EMAIL_REQUIRED = "Email is required!";
    String PASSWORD_REQUIRED = "Password can not be empty!";
    String PASSWORD_MISMATCH = "Password mismatch!";
    String MIN_PASS_LENGTH_ERR = "Password must be at least 8 characters long!";

    String INVALID_CREDENTIALS = "Invalid username or password!";
    String UNAUTHORIZED = "Unauthorized access!";
    String ACCESS_DENIED = "Access denied!";

    //OFFER
    String OFFER_CREATED = "Successfully registered new OFFER!";
    String OFFER_UPDATED = "Successfully updated OFFER!";
    String OFFER_DELETED = "Successfully deleted OFFER!";

    String OFFER_NOT_FOUND = "OFFER not found!";
}
