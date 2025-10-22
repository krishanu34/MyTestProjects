package com.mdot.exception;

/**
 * Exception class for handling errors related to SSO integration.
 * 
 * This class is used to signal issues occurring during SSO integration, 
 * such as compatibility problems or database normalization issues, 
 * thereby allowing the application to handle these errors gracefully.
 * 
 * Adheres to Java 21 and Spring Boot 3.1 standards, following 
 * the package structure and guidelines provided.
 */
public class SSOIntegrationException extends RuntimeException {

    /**
     * The error message associated with this exception.
     */
    private final String message;

    /**
     * Constructs a new SSOIntegrationException with the specified detail message.
     *
     * @param message The detail message explaining the nature of the exception.
     */
    public SSOIntegrationException(String message) {
        super(message); // Call the parent RuntimeException constructor
        this.message = message;
    }

    /**
     * Constructs a new SSOIntegrationException with the specified detail
     * message and cause.
     *
     * @param message The detail message explaining the nature of the exception.
     * @param cause   The cause (a throwable cause for this exception).
     */
    public SSOIntegrationException(String message, Throwable cause) {
        super(message, cause); // Call the parent RuntimeException constructor with the cause
        this.message = message;
    }

    /**
     * Gets the error message of the exception.
     *
     * @return The error message as a string.
     */
    @Override
    public String getMessage() {
        return message;
    }
}