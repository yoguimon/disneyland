package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.jhonny.models.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class GameRepository extends EntityRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(GameRepository.class);

    @Inject
    public GameRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Game> findAll() {
        return entityManager.createQuery("SELECT p FROM Game p", Game.class)
                .getResultList();
    }

}
