package org.jhonny.controller;

import io.vertx.ext.web.RequestBody;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jhonny.dto.BuyRequestDto;
import org.jhonny.dto.GameDto;
import org.jhonny.dto.RequestTicket;
import org.jhonny.dto.Response;
import org.jhonny.dto.ResponseObject;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.models.Ticket;
import org.jhonny.service.EmployeeService;
import org.jhonny.service.GameService;
import org.jhonny.service.ScheduleService;
import org.jhonny.service.TicketService;

import java.util.List;

@Path("/api")
@RequestScoped
public class EmployeeResource {

    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;
    private final GameService gameService;
    private final TicketService ticketService;

    @Inject
    public EmployeeResource(EmployeeService employeeService, ScheduleService scheduleService,
                            GameService gameService, TicketService ticketService) {
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
        this.gameService = gameService;
        this.ticketService = ticketService;
    }

    @POST
    @Path("/create/game")
    public Response createGame(GameDto game) {
        return employeeService.addNewGame(game);
    }

    @POST
    @Path("/add/schedule")
    public ResponseObject addSchedule(Schedule schedule) {
        return employeeService.addSchedule(schedule);
    }

    @POST
    @Path("/sell/ticket")
    public Response sellTicket(RequestTicket requestTicket) {
        return employeeService.sellTicket(requestTicket);
    }


    //pending to review
    @GET
    @Path("/game/list")
    public List<Game> getGamesWithSchedule() {
        return gameService.getGames();
    }
    //only for test
    @GET
    @Path("/schedule")
    public List<Schedule> getAllSchedules() {
        List<Schedule> res = ticketService.getSchedules();
        System.out.println(res);
        return res;

    }
}
