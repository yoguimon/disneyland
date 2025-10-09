package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.exception.ScheduleNotFoundException;
import org.jhonny.models.Schedules;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class ScheduleService{

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void addSchedule(Schedules schedule) {

        if(Objects.isNull(schedule)) {
            LOGGER.error("The schedule is null");
            throw new ScheduleNotFoundException("The schedule is null");

        }
        scheduleRepository.persist(schedule);
        LOGGER.info("Schedule added successfully");


    }
    public boolean checkSchedule(Set<Schedules> schedules, LocalTime hour) {
        for(Schedules schedule : schedules) {
            if(isItInsideSchedule(hour, schedule)){
                return true;
            }
        }
        return false;
    }
    private boolean isItInsideSchedule(LocalTime hour, Schedules schedule) {
        return hour.isAfter(schedule.getOpenTime()) && hour.isBefore(schedule.getCloseTime());
    }

    public List<Schedules> getAll(){
        return scheduleRepository.findAll().stream().toList();
    }
}
