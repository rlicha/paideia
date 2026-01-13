package io.paideia.backend;


import io.paideia.backend.exceptions.ResourceAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleResourceAlreadyRegistered(ResourceAlreadyExistsException exception) {
        return ResponseEntity
                .status(409)
                .body(exception.getMessage());
    }

}
