package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequestDto;
import org.jhonny.dto.GameDto;
import org.jhonny.dto.RequestTicket;
import org.jhonny.dto.Response;
import org.jhonny.dto.ResponseObject;
import org.jhonny.models.Schedule;
import org.jhonny.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Transactional
public class EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final GameService gameService;
    private final TicketService ticketService;
    private final ScheduleService scheduleService;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository, GameService gameService,
                           TicketService ticketService, ScheduleService scheduleService) {
        this.employeeRepository = employeeRepository;
        this.gameService = gameService;
        this.ticketService = ticketService;
        this.scheduleService = scheduleService;
    }
    public Response addNewGame(GameDto game) {

        LOGGER.info("Adding game");
        return gameService.addNewGame(game);
    }

    public ResponseObject addSchedule(Schedule schedule) {
        LOGGER.info("Adding schedule");
        return scheduleService.addSchedule(schedule);
    }

    public Response sellTicket(RequestTicket requestTicket) {

        LOGGER.info("Selling ticket has started");
        return ticketService.sellTicket(requestTicket);
    }

}
