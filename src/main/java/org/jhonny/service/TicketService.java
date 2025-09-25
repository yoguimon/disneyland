package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequestDto;
import org.jhonny.dto.RequestTicket;
import org.jhonny.dto.Response;
import org.jhonny.models.Buyer;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.models.Ticket;
import org.jhonny.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.jhonny.utils.ConstantQuery.FIND_ALL;
import static org.jhonny.utils.ConstantQuery.SCHEDULE;

@ApplicationScoped
@Transactional
public class TicketService {

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final TicketRepository ticketRepository;

    @Inject
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /// this method is not finished
    public Response sellTicket(RequestTicket requestTicket) {

        for(BuyRequestDto buyRequest : requestTicket.getBuyRequests()) {
           while(buyRequest.getAmount()<1){

               Game requestGame = ticketRepository.find(Game.class, buyRequest.getIdGame());

               if(Objects.isNull(requestGame)) {
                   LOGGER.error("Game not found");
                   return new Response(
                           "Game is not available"
                   );
               }

               Set<Schedule> schedules = requestGame.getSchedules();

               boolean isAvaliable = checkSchedule(schedules, buyRequest.getHour());
               if(!isAvaliable) {
                   LOGGER.info("The game is not available at this time");
                   return new Response(
                           "The game it is not avalible at that time"
                   );
               }

               Buyer buyer = ticketRepository.find(Buyer.class, buyRequest.getIdBuyer());

               if(Objects.isNull(buyer)) {
                   LOGGER.info("buyer not found");
                   return new Response(
                           "Buyer is not available"
                   );
               }
               String ticketCode = UUID.randomUUID().toString();
               LOGGER.info("ticket code generated");
               Ticket ticket = Ticket.builder()
                       .buyer(buyer)
                       .build();

               try{
                   ticketRepository.save(ticket);

                   LOGGER.info("Ticket has been saved");
                   return new Response(
                           "Ticket sold successfully"
                   );
               }catch(Exception e) {
                   LOGGER.error("Error saving ticket");
                   return new Response("Error saving ticket");
               }
           }
        }

        return null;
    }
    private boolean checkSchedule(Set<Schedule> schedules, LocalTime hour) {
        for(Schedule schedule : schedules) {
            if(isItInsideSchedule(hour, schedule)){
                return true;
            }
        }
        return false;
    }
    private boolean isItInsideSchedule(LocalTime hour, Schedule schedule) {
        return hour.isAfter(schedule.getStart()) && hour.isBefore(schedule.getEnd());
    }

    public List<Schedule> getSchedules() {
        String query = FIND_ALL.formatted(SCHEDULE);
        return ticketRepository.findALl(query, Schedule.class);
    }
}
