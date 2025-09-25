package org.jhonny.repository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jhonny.models.Schedule;

import java.util.List;

@Singleton
public class ScheduleRepository extends EntityRepository {

    @Inject
    public ScheduleRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Schedule> getALlSchedules(){
        TypedQuery<Schedule> query = entityManager.createQuery("SELECT h FROM Schedule h", Schedule.class);
        return query.getResultList();
    }

}
