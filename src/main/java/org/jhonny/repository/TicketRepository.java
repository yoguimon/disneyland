package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TicketRepository extends EntityRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(TicketRepository.class);

    @Inject
    public TicketRepository(EntityManager entityManager) {
        super(entityManager);
    }

}
