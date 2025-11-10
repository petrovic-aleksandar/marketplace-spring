package me.aco.marketplace.exception;

/**
 * Thrown when a requested resource (user, item, etc.) cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
