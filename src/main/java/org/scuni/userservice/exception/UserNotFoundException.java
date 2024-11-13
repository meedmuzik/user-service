package org.scuni.userservice.exception;

public class UserNotFoundException extends DefaultUserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
