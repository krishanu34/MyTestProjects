package com.mdot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for SSO integration responses.
 * <p>
 * This class encapsulates the response details for SSO integration.
 * It contains attributes to represent the status and message of the response.
 * Strict adherence to the provided structure is maintained, ensuring compatibility
 * with Java 21 and Spring Boot 3.1 standards.
 * </p>
 */
public class SSOResponseDTO {

    /**
     * Represents the status of the SSO integration process.
     * This field is mandatory and cannot be null or empty.
     */
    @NotNull(message = "Status cannot be null")
    @NotEmpty(message = "Status cannot be empty")
    private String status;

    /**
     * Represents the message associated with the SSO integration response.
     * This provides additional details or context regarding the integration status.
     */
    @NotNull(message = "Message cannot be null")
    @NotEmpty(message = "Message cannot be empty")
    private String message;

    // Default constructor needed for frameworks like Spring and Jackson
    public SSOResponseDTO() {
    }

    /**
     * Parameterized constructor for creating a new SSOResponseDTO with specific values.
     *
     * @param status  The status of the SSO integration.
     * @param message The message providing details about the response.
     */
    public SSOResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the status.
     *
     * @return The status of the SSO integration.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the message.
     *
     * @return The message of the SSO integration response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Overridden `toString()` for better representation of the response object.
     *
     * @return A string representation of the SSOResponseDTO.
     */
    @Override
    public String toString() {
        return "SSOResponseDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}