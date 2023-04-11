package com.parking.buddy.exception;


public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistsException(String resource, String field, Object value) {
        super(String.format("%s already exists with %s : '%s'", resource, field, value));
    }

}


