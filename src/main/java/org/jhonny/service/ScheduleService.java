package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.ResponseObject;
import org.jhonny.models.Schedule;
import org.jhonny.repository.ScheduleRepository;
import org.jhonny.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
@Transactional
public class ScheduleService{

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseObject addSchedule(Schedule schedule) {

        if(Objects.isNull(schedule)) {
            return new ResponseObject(
                    "schecule is null",
                    Status.ERROR_404,
                    null
            );
        }
        try{
            scheduleRepository.save(schedule);
            LOGGER.info("Schedule added successfully");

            return new ResponseObject(
                    "Schedule added successfully",
                    Status.SUCCESS,
                    schedule
            );
        }catch(Exception e){
            LOGGER.error("Error while adding schedule", e);
            return new ResponseObject(
                    "Error while adding schedule",
                    Status.ERROR,
                    null
            );

        }
    }
}
