package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jhonny.models.Schedule;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository<Schedule> {

}
