package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class UserRepository extends EntityRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @Inject
    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }

}
