package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AdminRepository extends EntityRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminRepository.class);

    @Inject
    public AdminRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
