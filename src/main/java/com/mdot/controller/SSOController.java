package com.mdot.controller;

import com.mdot.dto.SSORequestDTO;
import com.mdot.dto.SSOResponseDTO;
import com.mdot.exception.SSOIntegrationException;
import com.mdot.service.SSOService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SSOController handles Single Sign-On (SSO) integration requests.
 * <p>
 * This class represents the Controller layer in the application architecture. It interacts with the Service layer 
 * to process the SSO integration request from external clients while managing validation and exception handling.
 * Adheres to Java 21 and Spring Boot 3.1 compatibility standards.
 */
@RestController
public class SSOController {

    private final SSOService ssoService;

    /**
     * Constructor-based dependency injection for SSOController.
     *
     * @param ssoService The service layer responsible for SSO integration logic.
     */
    @Autowired
    public SSOController(SSOService ssoService) {
        this.ssoService = ssoService;
    }

    /**
     * Endpoint to process SSO integration requests.
     * <p>
     * This method maps to a POST request and interacts with the service layer to execute the business logic for SSO integration.
     * Input validation is performed using Jakarta Bean Validation (@Valid).
     *
     * @param ssoRequest The request DTO containing SSO details.
     * @return A ResponseEntity encapsulating the SSOResponseDTO and HTTP status.
     * @throws SSOIntegrationException If any integration-related errors occur.
     */
    @PostMapping("/api/v1/sso/integrate")
    public ResponseEntity<SSOResponseDTO> integrateSSO(@Valid @RequestBody SSORequestDTO ssoRequest) {
        try {
            // Delegate the request to the service layer to perform the integration logic
            SSOResponseDTO response = ssoService.integrateSSO(ssoRequest);
            
            // Return the successful response with HTTP 200 OK status
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SSOIntegrationException ex) {
            // Handle known exceptions gracefully
            throw ex;
        } catch (Exception ex) {
            // Handle unexpected exceptions and wrap them in an SSOIntegrationException
            throw new SSOIntegrationException("An unexpected error occurred while processing the SSO integration request.", ex);
        }
    }

    /**
     * Global Exception Handler for SSOIntegrationException.
     * <p>
     * This method captures any SSOIntegrationException thrown during the SSO integration process
     * and returns an appropriate error response to the client.
     *
     * @param ex The exception captured during integration.
     * @return A ResponseEntity encapsulating the error message and HTTP status.
     */
    @ExceptionHandler(SSOIntegrationException.class)
    public ResponseEntity<String> handleSSOIntegrationException(SSOIntegrationException ex) {
        // Log the exception for debugging (if a logging framework is available)
        System.err.println("Error occurred during SSO integration: " + ex.getMessage());
        
        // Return the error response with HTTP 500 Internal Server Error status
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}