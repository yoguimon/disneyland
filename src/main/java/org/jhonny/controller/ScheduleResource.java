package org.jhonny.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.exception.ScheduleNotFoundException;
import org.jhonny.models.Schedules;
import org.jhonny.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("/api/v1/schedules")
public class ScheduleResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(ScheduleResource.class);

    private final ScheduleService scheduleService;

    @Inject
    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @POST
    public Response addSchedule(Schedules schedule) {

        try{

            scheduleService.addSchedule(schedule);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("message", "Scheadule added succesfully",
                                    "scheadule", schedule
                            ))
                    .build();
        }catch(ScheduleNotFoundException e){
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
