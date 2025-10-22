package com.mdot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Entity class representing the SSO Integration details.
 * <p>
 * This entity is mapped to a database table for storing SSO-related information.
 * The structure ensures compatibility with MDOT's Oracle environment while adhering to
 * database normalization principles to optimize server performance.
 * </p>
 */
@Entity
@Table(name = "sso_entity")
public class SSOEntity {

    /**
     * The primary key for the SSO entity.
     * Auto-generated using the database's identity generation strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username for the SSO integration.
     * This field is mandatory and cannot be null or empty.
     */
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    /**
     * Details of the Oracle schema associated with the SSO integration.
     * This field is mandatory and ensures compatibility with MDOT's existing Oracle setup.
     */
    @NotNull(message = "Oracle schema details cannot be null")
    @NotEmpty(message = "Oracle schema details cannot be empty")
    private String oracleSchemaDetails;

    /**
     * Default constructor required for JPA and frameworks like Spring.
     */
    public SSOEntity() {
    }

    /**
     * Parameterized constructor for creating an SSOEntity instance with specific values.
     *
     * @param username           The username associated with the SSO integration.
     * @param oracleSchemaDetails Details of the Oracle schema for the SSO integration.
     */
    public SSOEntity(String username, String oracleSchemaDetails) {
        this.username = username;
        this.oracleSchemaDetails = oracleSchemaDetails;
    }

    /**
     * Gets the ID of the SSO entity.
     *
     * @return The ID of the entity.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the SSO entity.
     *
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username associated with the SSO integration.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the SSO integration.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the Oracle schema details associated with the SSO integration.
     *
     * @return The Oracle schema details.
     */
    public String getOracleSchemaDetails() {
        return oracleSchemaDetails;
    }

    /**
     * Sets the Oracle schema details for the SSO integration.
     *
     * @param oracleSchemaDetails The Oracle schema details to set.
     */
    public void setOracleSchemaDetails(String oracleSchemaDetails) {
        this.oracleSchemaDetails = oracleSchemaDetails;
    }

    /**
     * Provides a string representation of the SSO entity.
     *
     * @return A string representation of the SSOEntity.
     */
    @Override
    public String toString() {
        return "SSOEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", oracleSchemaDetails='" + oracleSchemaDetails + '\'' +
                '}';
    }
}