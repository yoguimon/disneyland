package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.ScheduleRequest;
import org.jhonny.exception.ScheduleNotFoundException;
import org.jhonny.models.Schedule;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ScheduleService{

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void addSchedule(ScheduleRequest scheduleRequest) {
        if(Objects.isNull(scheduleRequest)) {
            LOGGER.error("The schedule is null");
            throw new ScheduleNotFoundException("The schedule is null");

        }
        Schedule schedule = Schedule.builder()
                .openTime(scheduleRequest.openTime())
                .closeTime(scheduleRequest.closeTime())
                .dayOfWeek(scheduleRequest.dayOfWeek())
                .build();

        scheduleRepository.persist(schedule);
        LOGGER.info("Schedule added successfully");
    }

    public List<Schedule> getAll(){
        /// we can get from cache
        return scheduleRepository.findAll().stream().toList();
    }
}
