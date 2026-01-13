package io.paideia.backend.exceptions;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    protected ResourceAlreadyExistsException(String resourceType, String resourceName) {
        super(String.format("%s %s already exists.", resourceType, resourceName));
    }
}
