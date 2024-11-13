package org.scuni.userservice.exception;

public class UserAlreadyExistException extends DefaultUserServiceException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
