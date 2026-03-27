package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequest;
import org.jhonny.dto.TicketRequest;
import org.jhonny.dto.TicketResponse;
import org.jhonny.models.Buyer;
import org.jhonny.models.Game;
import org.jhonny.models.Sale;
import org.jhonny.models.Schedule;
import org.jhonny.models.Ticket;
import org.jhonny.models.TicketDetail;
import org.jhonny.models.TicketBooth;
import org.jhonny.repository.SaleRepository;
import org.jhonny.repository.TicketDetailRepository;
import org.jhonny.repository.TicketBoothRepository;
import org.jhonny.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class SaleService {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    private final TicketRepository ticketRepository;
    private final TicketDetailRepository ticketDetailRepository;
    private final ScheduleService scheduleService;
    private final TicketBoothRepository ticketOfficeRepository;
    private final SaleRepository saleRepository;
    private final GameService gameService;
    private final BuyerService buyerService;

    @Inject
    public SaleService(TicketRepository ticketRepository, TicketDetailRepository ticketDetailRepository,
                         ScheduleService scheduleService, TicketBoothRepository ticketOfficeRepository,
                         SaleRepository saleRepository, GameService gameService,
                         BuyerService buyerService) {
        this.ticketRepository = ticketRepository;
        this.ticketDetailRepository = ticketDetailRepository;
        this.scheduleService = scheduleService;
        this.ticketOfficeRepository = ticketOfficeRepository;
        this.saleRepository = saleRepository;
        this.gameService = gameService;
        this.buyerService = buyerService;
    }

    @Transactional
    public TicketResponse sellTicket(TicketRequest requestTicket){
        Buyer buyer = buyerService.getBuyer(requestTicket.buyerId());

        TicketBooth ticketOffice = ticketOfficeRepository.findById(requestTicket.ticketOfficeId());

        Sale sale = Sale.builder()
                .dateOfSale(LocalDate.now())
                .ticketOffice(ticketOffice)
                .build();
        saleRepository.persist(sale);

        Ticket ticket = Ticket.builder()
                .buyer(buyer)
                .sale(sale)
                .build();

        ticketRepository.persist(ticket);

        List<TicketDetail> ticketDetails = new ArrayList<>();
        double total = 0;

        for(BuyRequest buyRequest : requestTicket.buyRequests()) {
            Game requestGame = gameService.getGame(buyRequest.gameId());

            List<Schedule> schedules = requestGame.getSchedules();

            boolean isAvaliable = scheduleService.checkSchedule(schedules, buyRequest.hour());
            if(!isAvaliable) {
                LOGGER.error("The game is not available at this time");

                TicketDetail ticketDetail = TicketDetail.builder()
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

    private void generateTicket(BuyRequest buyRequest, Game requestGame, Ticket ticket, List<TicketDetail> ticketDetails) {
        String ticketCode = UUID.randomUUID().toString();
        LOGGER.info("ticket code generated");

        TicketDetail ticketDetail = TicketDetail.builder()
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

    public Long getNumberOfTicketsSoldForAllGameIntoASpecificRangeDate(LocalDate startDate, LocalDate endDate){
        return null;
    }
}
