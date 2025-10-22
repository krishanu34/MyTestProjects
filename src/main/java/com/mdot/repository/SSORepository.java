package com.mdot.repository;

import com.mdot.entity.SSOEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for SSOEntity operations.
 * <p>
 * This interface is responsible for persisting SSOEntity objects into the database.
 * It extends JpaRepository to leverage Spring Data JPA features like CRUD operations,
 * custom queries, and pagination.
 * </p>
 * <ul>
 *     <li><strong>Type:</strong> Interface</li>
 *     <li><strong>Package:</strong> com.mdot.repository</li>
 *     <li><strong>Responsibilities:</strong>
 *     <ul>
 *         <li>Provide methods for storing and retrieving SSOEntity objects.</li>
 *         <li>Ensure compatibility with MDOT's Oracle environment.</li>
 *         <li>Support Spring Boot 3.1 and Java 21 standards.</li>
 *     </ul>
 *     </li>
 * </ul>
 * <p>Maintains strict adherence to given structure and best practices.</p>
 */
@Repository
public interface SSORepository extends JpaRepository<SSOEntity, Long> {

    /**
     * Save the given SSOEntity into the database.
     * <p>
     * Overrides the save method from JpaRepository to provide additional documentation
     * and ensure alignment with the provided skeleton.
     * </p>
     *
     * @param ssoEntity The entity representing SSO integration data.
     * @return The saved SSOEntity with an updated ID.
     * @throws com.mdot.exception.DatabaseException If a database error occurs.
     */
    @Override
    SSOEntity save(SSOEntity ssoEntity);

}