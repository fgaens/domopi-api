package be.codesolutions.domopi.infrastructure.persistence.repository;

import be.codesolutions.domopi.infrastructure.persistence.entity.OutputEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OutputRepository implements PanacheRepositoryBase<OutputEntity, String> {
    // Inherits basic CRUD operations from PanacheRepositoryBase
    // Used to load initial data from database at startup
}