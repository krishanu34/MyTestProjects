package com.mdot.service;

import com.mdot.dto.SSORequestDTO;
import com.mdot.dto.SSOResponseDTO;
import com.mdot.entity.SSOEntity;
import com.mdot.exception.SSOIntegrationException;
import com.mdot.repository.SSORepository;
import com.mdot.util.DatabaseUtil;
import com.mdot.util.ValidationUtil;
import com.mdot.external.ExternalOracleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the SSOService interface.
 * <p>
 * This service acts as the business layer for handling SSO integration.
 * It ensures MDOT's Oracle compatibility, validates input requests,
 * addresses database normalization issues, and persists the integration data.
 * Adheres to Java 21 and Spring Boot 3.1 standards as per project requirements.
 * <p>
 * Interactions:
 * <ul>
 *     <li>Validates incoming SSO requests using {@link ValidationUtil}.</li>
 *     <li>Checks Oracle compatibility through {@link ExternalOracleService}.</li>
 *     <li>Normalizes database schema using {@link DatabaseUtil}.</li>
 *     <li>Persists data via {@link SSORepository}.</li>
 * </ul>
 */
@Service
public class SSOServiceImpl implements SSOService {

    // Dependencies injected by Spring
    private final ValidationUtil validationUtil;
    private final ExternalOracleService externalOracleService;
    private final DatabaseUtil databaseUtil;
    private final SSORepository ssoRepository;

    /**
     * Constructor-based dependency injection for SSOServiceImpl.
     *
     * @param validationUtil         Utility for validating SSO request DTOs.
     * @param externalOracleService  External service for Oracle compatibility checks.
     * @param databaseUtil           Utility for schema normalization.
     * @param ssoRepository          Repository for persisting SSO entities.
     */
    @Autowired
    public SSOServiceImpl(
            ValidationUtil validationUtil,
            ExternalOracleService externalOracleService,
            DatabaseUtil databaseUtil,
            SSORepository ssoRepository) {
        this.validationUtil = validationUtil;
        this.externalOracleService = externalOracleService;
        this.databaseUtil = databaseUtil;
        this.ssoRepository = ssoRepository;
    }

    /**
     * Performs SSO integration by validating inputs, verifying Oracle compatibility,
     * normalizing the database schema, and storing the integration data in the database.
     *
     * @param ssoRequestDTO The SSO request DTO containing integration details.
     * @return A response DTO indicating the integration status.
     * @throws ValidationException       If validation of the input request fails.
     * @throws SSOIntegrationException   If Oracle compatibility or normalization fails.
     */
    @Override
    @Transactional
    public SSOResponseDTO integrateSSO(@Valid SSORequestDTO ssoRequestDTO) {
        try {
            // Step 1: Validate the input SSO request
            validationUtil.validateSSORequest(ssoRequestDTO);

            // Step 2: Verify compatibility with Oracle-compatible systems
            boolean isCompatible = externalOracleService.verifyCompatibility(ssoRequestDTO.getOracleSchemaDetails());
            if (!isCompatible) {
                throw new SSOIntegrationException("Oracle compatibility check failed.");
            }

            // Step 3: Normalize database schema to optimize server usage
            validateDatabaseNormalization();

            // Step 4: Persist integration details into the database
            SSOEntity ssoEntity = new SSOEntity();
            ssoEntity.setUsername(ssoRequestDTO.getUsername());
            ssoEntity.setOracleSchemaDetails(ssoRequestDTO.getOracleSchemaDetails());
            SSOEntity savedEntity = ssoRepository.save(ssoEntity);

            // Step 5: Prepare response DTO
            SSOResponseDTO responseDTO = new SSOResponseDTO("Success", "SSO Integration completed successfully.");
            return responseDTO;

        } catch (Exception e) {
            // Handle any unexpected exceptions gracefully
            throw new SSOIntegrationException("An error occurred during SSO integration.", e);
        }
    }

    /**
     * Helper method to validate and address database normalization issues.
     *
     * @throws SSOIntegrationException If database normalization fails.
     */
    private void validateDatabaseNormalization() {
        try {
            databaseUtil.normalizeSchema();
        } catch (Exception e) {
            throw new SSOIntegrationException("Failed to normalize the database schema.", e);
        }
    }
}