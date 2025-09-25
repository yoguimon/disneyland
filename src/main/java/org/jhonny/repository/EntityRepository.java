package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class EntityRepository {

    protected final EntityManager entityManager;

    @Inject
    public EntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Object entity) {
        entityManager.persist(entity);
    }
    public <T> T find(Class<T> entity, Long id) {
        return entityManager.find(entity, id);
    }

    public <T> List<T> findALl(String query, Class<T> entity) {
        TypedQuery<T> result = entityManager.createQuery(query, entity);
        return result.getResultList();
    }

    public void saveMany(List<?> entities) {
        for (Object entity : entities) {
            entityManager.persist(entity);
        }
    }

}
