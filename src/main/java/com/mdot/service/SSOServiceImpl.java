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

import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation for the SSOService interface.
 * <p>
 * This service handles the business logic for SSO integration and ensures compatibility
 * with MDOT's Oracle environment.
 *
 * <ul>
 *     <li>Validates SSO requests using {@link ValidationUtil}.</li>
 *     <li>Verifies Oracle compatibility via {@link ExternalOracleService}.</li>
 *     <li>Performs asynchronous database normalization to optimize performance.</li>
 *     <li>Handles persistence using {@link SSORepository}.</li>
 * </ul>
 */
@Service
public class SSOServiceImpl implements SSOService {

    private final ValidationUtil validationUtil;
    private final ExternalOracleService externalOracleService;
    private final DatabaseUtil databaseUtil;
    private final SSORepository ssoRepository;
    private final ExecutorService executorService;

    /**
     * Constructor-based dependency injection.
     * Configured to inject a pre-defined {@link ExecutorService} for better resource management.
     *
     * @param validationUtil         Utility for validation logic.
     * @param externalOracleService  Service to verify Oracle schema compatibility.
     * @param databaseUtil           Utility for database normalization.
     * @param ssoRepository          Repository to manage database persistence.
     * @param executorService        Executor service for asynchronous tasks.
     */
    @Autowired
    public SSOServiceImpl(
            ValidationUtil validationUtil,
            ExternalOracleService externalOracleService,
            DatabaseUtil databaseUtil,
            SSORepository ssoRepository,
            ExecutorService executorService) {
        this.validationUtil = validationUtil;
        this.externalOracleService = externalOracleService;
        this.databaseUtil = databaseUtil;
        this.ssoRepository = ssoRepository;
        this.executorService = executorService;
    }

    /**
     * Executes the SSO integration process.
     *
     * @param ssoRequestDTO The request DTO containing integration details.
     * @return Response DTO indicating success or failure.
     * @throws SSOIntegrationException For validation, compatibility, or persistence-related issues.
     */
    @Override
    public SSOResponseDTO integrateSSO(@Valid SSORequestDTO ssoRequestDTO) {
        try {
            // Step 1: Validate input data
            validationUtil.validateSSORequest(ssoRequestDTO);

            // Step 2: Verify Oracle compatibility
            boolean isCompatible = externalOracleService.verifyCompatibility(ssoRequestDTO.getOracleSchemaDetails());
            if (!isCompatible) {
                throw new SSOIntegrationException("Oracle schema validation failed: Non-compatible schema.");
            }

            // Step 3: Normalize the database schema asynchronously
            runDatabaseNormalizationAsync();

            // Step 4: Persist the integration details
            persistIntegrationDetails(ssoRequestDTO);

            // Step 5: Return success response
            return new SSOResponseDTO("Success", "SSO Integration completed successfully.");

        } catch (ValidationException e) {
            throw new SSOIntegrationException("Validation failed for SSO request.", e);
        } catch (CompatibilityException e) {
            throw new SSOIntegrationException("Oracle compatibility check failed.", e);
        } catch (PersistenceException e) {
            throw new SSOIntegrationException("Failed to persist SSO integration details.", e);
        }
    }

    /**
     * Executes database normalization asynchronously to avoid performance bottlenecks.
     * Logs any errors, retries once on failure, and ensures proper error handling.
     */
    private void runDatabaseNormalizationAsync() {
        executorService.submit(() -> {
            boolean success = false;
            for (int attempt = 1; attempt <= 2; attempt++) { // Retry logic: 2 attempts
                try {
                    databaseUtil.normalizeSchema();
                    success = true; // If successful, exit the loop
                    System.out.println("Database normalization completed successfully on attempt " + attempt);
                    break;
                } catch (Exception e) {
                    System.err.println("Attempt " + attempt + " to normalize database failed: " + e.getMessage());
                    if (attempt == 2) {
                        throw new SSOIntegrationException("Failed to execute database normalization.", e);
                    }
                }
            }
            if (!success) {
                System.err.println("Database normalization could not be completed after multiple attempts.");
            }
        });
    }

    /**
     * Persists integration details into the database.
     *
     * @param ssoRequestDTO The request DTO containing integration details.
     * @throws PersistenceException If database operations fail.
     */
    @Transactional
    void persistIntegrationDetails(SSORequestDTO ssoRequestDTO) {
        try {
            SSOEntity ssoEntity = new SSOEntity();
            ssoEntity.setUsername(ssoRequestDTO.getUsername());
            ssoEntity.setOracleSchemaDetails(ssoRequestDTO.getOracleSchemaDetails());
            ssoRepository.save(ssoEntity);
        } catch (Exception e) {
            throw new PersistenceException("Failed to persist SSO integration details.", e);
        }
    }
}