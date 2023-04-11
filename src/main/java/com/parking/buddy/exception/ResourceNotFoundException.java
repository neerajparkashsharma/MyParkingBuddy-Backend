package com.parking.buddy.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s not found with %s : '%s'", resource, field, value));
    }

    public ResourceNotFoundException(String resource, String field, Object value, Throwable cause) {
        super(String.format("%s not found with %s : '%s'", resource, field, value), cause);
    }

}
