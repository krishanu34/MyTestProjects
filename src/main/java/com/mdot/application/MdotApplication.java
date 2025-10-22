package com.mdot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MdotApplication is the entry point for the Spring Boot application.
 * <p>
 * This class is responsible for bootstrapping and launching the MDOT application.
 * It adheres to the structure and requirements specified in the skeleton, 
 * avoiding additional attributes, methods, or redundant logic.
 * </p>
 * <p>
 * Usage of Java 21 and Spring Boot 3.1 ensures compliance with modern standards, 
 * leveraging `SpringApplication.run()` to initialize the application context.
 * </p>
 */
@SpringBootApplication
public class MdotApplication {

    /**
     * Main method that serves as the entry point for the Spring Boot application.
     * <p>
     * This method initializes and starts the Spring Boot application by invoking
     * {@link SpringApplication#run(Class, String...)} with `MdotApplication.class` as the argument.
     * </p>
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Bootstraps the Spring Boot application
        SpringApplication.run(MdotApplication.class, args);
    }
}