package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequest;
import org.jhonny.dto.TicketRequest;
import org.jhonny.dto.TicketResponse;
import org.jhonny.exception.BuyerNotFoundException;
import org.jhonny.exception.GameNotAvaliableException;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.models.Buyers;
import org.jhonny.models.Games;
import org.jhonny.models.Sales;
import org.jhonny.models.Schedules;
import org.jhonny.models.Tickets;
import org.jhonny.models.TicketDetails;
import org.jhonny.models.TicketOffices;
import org.jhonny.repository.BuyerRepository;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.SaleRepository;
import org.jhonny.repository.TicketDetailRepository;
import org.jhonny.repository.TicketOfficeRepository;
import org.jhonny.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class SaleService {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    private final TicketRepository ticketRepository;
    private final TicketDetailRepository ticketDetailRepository;
    private final ScheduleService scheduleService;
    private final BuyerRepository buyerRepository;
    private final GameRepository gameRepository;
    private final TicketOfficeRepository ticketOfficeRepository;
    private final SaleRepository saleRepository;

    @Inject
    public SaleService(TicketRepository ticketRepository, TicketDetailRepository ticketDetailRepository,
                         ScheduleService scheduleService, BuyerRepository buyerRepository,
                         GameRepository gameRepository, TicketOfficeRepository ticketOfficeRepository,
                         SaleRepository saleRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketDetailRepository = ticketDetailRepository;
        this.scheduleService = scheduleService;
        this.buyerRepository = buyerRepository;
        this.gameRepository = gameRepository;
        this.ticketOfficeRepository = ticketOfficeRepository;
        this.saleRepository = saleRepository;
    }

    @Transactional
    public TicketResponse sellTicket(TicketRequest requestTicket){
        Buyers buyer = getBuyer(requestTicket.buyerId());

        TicketOffices ticketOffice = ticketOfficeRepository.findById(requestTicket.ticketOfficeId());

        Sales sale = Sales.builder()
                .dateOfSale(LocalDate.now())
                .ticketOffice(ticketOffice)
                .build();
        saleRepository.persist(sale);

        Tickets ticket = Tickets.builder()
                .buyer(buyer)
                .sale(sale)
                .build();

        ticketRepository.persist(ticket);

        List<TicketDetails> ticketDetails = new ArrayList<>();
        double total = 0;

        for(BuyRequest buyRequest : requestTicket.buyRequests()) {
            Games requestGame = getGame(buyRequest.gameId());

            Set<Schedules> schedules = requestGame.getSchedules();

            boolean isAvaliable = scheduleService.checkSchedule(schedules, buyRequest.hour());
            if(!isAvaliable) {
                LOGGER.error("The game is not available at this time");

                TicketDetails ticketDetail = TicketDetails.builder()
                        .game(requestGame)
                        .hour(buyRequest.hour())
                        .build();
                return new TicketResponse(
                        "For this schedule is not available the game",
                        List.of(ticketDetail)
                );

            }

            int numberOfTicketsForAGame = buyRequest.amount();
            total += numberOfTicketsForAGame*(requestGame.getPrice().toBigInteger().doubleValue());

            while(numberOfTicketsForAGame>0){

                generateTicket(buyRequest, requestGame, ticket, ticketDetails);
                numberOfTicketsForAGame--;

            }
        }
        ticketDetailRepository.persist(ticketDetails);
        sale.setTotalSale(total);
        saleRepository.persist(sale);

        LOGGER.info("Ticket has been sold");
        return new TicketResponse(
                "Ticket has been sold",
                ticketDetails
        );
    }

    private void generateTicket(BuyRequest buyRequest, Games requestGame, Tickets ticket, List<TicketDetails> ticketDetails) {
        String ticketCode = UUID.randomUUID().toString();
        LOGGER.info("ticket code generated");

        TicketDetails ticketDetail = TicketDetails.builder()
                .code(ticketCode)
                .price(requestGame.getPrice())
                .dayOfWeek(buyRequest.dayOfWeek())
                .dateOfGame(buyRequest.dateOfGame())
                .hour(buyRequest.hour())
                .game(requestGame)
                .ticket(ticket)
                .build();
        ticketDetails.add(ticketDetail);
    }

    private Games getGame(Long id){
        Games requestGame = gameRepository.findById(id);

        if(Objects.isNull(requestGame)) {
            LOGGER.error("Game not found");
            throw new GameNotFoundException("Game not found");
        }
        return requestGame;
    }
    private Buyers getBuyer(Long id){
        Buyers buyer = buyerRepository.findById(id);

        if(Objects.isNull(buyer)) {
            LOGGER.error("buyer not found");
            throw new BuyerNotFoundException("buyer not found");

        }
        return buyer;
    }

    public Long getNumberOfTicketsSoldForAllGameIntoASpecificRangeDate(LocalDate startDate, LocalDate endDate){
        return null;
    }
}
