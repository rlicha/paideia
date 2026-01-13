package io.paideia.backend.exceptions;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {

    public UserAlreadyExistsException(String username) {
        super("User", username) ;
    }
}
