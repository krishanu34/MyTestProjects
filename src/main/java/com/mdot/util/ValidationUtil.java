package com.mdot.util;

import com.mdot.dto.SSORequestDTO;
import com.mdot.exception.ValidationException;

import jakarta.validation.constraints.NotNull;

/**
 * Utility class for validating SSO integration requests and ensuring compliance
 * with MDOT's Oracle environment. Adheres to the single responsibility principle, 
 * focusing solely on validation logic for the `SSORequestDTO`.
 *
 * <p>Strictly follows the provided skeleton structure and avoids logic duplication.</p>
 */
public class ValidationUtil {

    /**
     * Validates the SSO request DTO to ensure integrity, compliance with MDOT's Oracle environment,
     * and adherence to the business rules.
     *
     * @param ssoRequestDTO The Data Transfer Object (DTO) containing the SSO request details.
     * @throws ValidationException If validation checks fail, such as missing or invalid data.
     */
    public void validateSSORequest(@NotNull SSORequestDTO ssoRequestDTO) {

        if (ssoRequestDTO == null) {
            throw new ValidationException("SSORequestDTO is null. Validation cannot be performed.");
        }

        // Validate username
        if (ssoRequestDTO.getUsername() == null || ssoRequestDTO.getUsername().isBlank()) {
            throw new ValidationException("Username cannot be null, empty, or blank.");
        }

        // Validate Oracle schema details
        if (ssoRequestDTO.getOracleSchemaDetails() == null || ssoRequestDTO.getOracleSchemaDetails().isBlank()) {
            throw new ValidationException("Oracle schema details cannot be null, empty, or blank.");
        }

        // Placeholder for additional validation logic specific to the Oracle schema format
        if (!isOracleSchemaValid(ssoRequestDTO.getOracleSchemaDetails())) {
            throw new ValidationException("Oracle schema details are invalid or incompatible with MDOT's Oracle environment.");
        }
    }

    /**
     * Helper method to validate the Oracle schema format.
     * <p>
     * This method ensures that the provided Oracle schema details conform to expected
     * formatting or compatibility standards. Extended business rules can be applied here as required.
     * </p>
     *
     * @param oracleSchemaDetails The Oracle schema details provided in the request.
     * @return true if the schema format is valid; false otherwise.
     */
    private boolean isOracleSchemaValid(String oracleSchemaDetails) {
        // Simulated validation logic (can be replaced with real business logic when integrated)
        // For example, checking for specific keywords or patterns in the schema details
        return oracleSchemaDetails.contains("oracle-compatible");
    }
}