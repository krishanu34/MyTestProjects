package com.mdot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for SSO integration requests.
 * <p>
 * This class is used to encapsulate the request details needed for SSO integration.
 * It contains attributes for the username and Oracle schema details, each marked
 * with validation constraints to ensure the integrity of data being passed through the application.
 * </p>
 */
public class SSORequestDTO {

    /**
     * The username of the user initiating the SSO request.
     * This field is mandatory and cannot be null or empty.
     */
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    /**
     * Details of the Oracle schema that will be used for the SSO integration.
     * This field is mandatory and cannot be null or empty.
     */
    @NotNull(message = "Oracle schema details cannot be null")
    @NotEmpty(message = "Oracle schema details cannot be empty")
    private String oracleSchemaDetails;

    // Default constructor needed for frameworks like Spring and Jackson
    public SSORequestDTO() {
    }

    /**
     * Parameterized constructor for creating a new SSORequestDTO with specific values.
     *
     * @param username           The username associated with the SSO request.
     * @param oracleSchemaDetails The Oracle schema details for the SSO integration.
     */
    public SSORequestDTO(String username, String oracleSchemaDetails) {
        this.username = username;
        this.oracleSchemaDetails = oracleSchemaDetails;
    }

    /**
     * Gets the username.
     *
     * @return The username associated with the SSO request.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the Oracle schema details.
     *
     * @return The Oracle schema details associated with the SSO request.
     */
    public String getOracleSchemaDetails() {
        return oracleSchemaDetails;
    }

    /**
     * Sets the Oracle schema details.
     *
     * @param oracleSchemaDetails The Oracle schema details to set.
     */
    public void setOracleSchemaDetails(String oracleSchemaDetails) {
        this.oracleSchemaDetails = oracleSchemaDetails;
    }

    @Override
    public String toString() {
        return "SSORequestDTO{" +
                "username='" + username + '\'' +
                ", oracleSchemaDetails='" + oracleSchemaDetails + '\'' +
                '}';
    }
}