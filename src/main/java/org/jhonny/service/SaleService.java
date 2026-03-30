package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequest;
import org.jhonny.dto.TicketDetail;
import org.jhonny.dto.TicketRequest;
import org.jhonny.dto.TicketResponse;
import org.jhonny.models.Client;
import org.jhonny.models.Game;
import org.jhonny.models.Sale;
import org.jhonny.models.SaleDetail;
import org.jhonny.models.TicketOffice;
import org.jhonny.repository.ClientRepository;
import org.jhonny.repository.SaleDetailRepository;
import org.jhonny.repository.SaleRepository;
import org.jhonny.repository.TicketOfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SaleService {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    private final TicketOfficeRepository ticketOfficeRepository;
    private final SaleRepository saleRepository;
    private final GameService gameService;
    private final ClientService clientService;
    private final SaleDetailRepository saleDetailRepository;

    @Inject
    public SaleService(TicketOfficeRepository ticketOfficeRepository,
                         SaleRepository saleRepository, GameService gameService,
                         ClientService clientService, SaleDetailRepository saleDetailRepository) {
        this.ticketOfficeRepository = ticketOfficeRepository;
        this.saleRepository = saleRepository;
        this.gameService = gameService;
        this.clientService = clientService;
        this.saleDetailRepository = saleDetailRepository;
    }

    @Transactional
    public TicketResponse sellTicket(TicketRequest requestTicket){
        Client client = clientService.getClient(requestTicket.clientId());

        /// we can get it from cache
        TicketOffice ticketOffice = ticketOfficeRepository.findById(requestTicket.ticketOfficeId());

        Sale sale = Sale.builder()
                .dateOfSale(LocalDate.now())
                .client(client)
                .ticketOffice(ticketOffice)
                .amount(requestTicket.amount())
                .total(requestTicket.total())
                .build();
        saleRepository.persist(sale);

        List<TicketDetail> ticketDetails = new ArrayList<>();
        List<SaleDetail> saleDetails = new ArrayList<>();

        ///we do not need to validate,

        for(BuyRequest buyRequest : requestTicket.buyRequests()) {
            /// we can use cache to get it
            Game requestGame = gameService.getGame(buyRequest.gameId());

            int numberOfTicketsForAGame = buyRequest.amountTicket();
            while(numberOfTicketsForAGame>0){
                generateTicket(buyRequest, requestGame, ticketDetails);

                SaleDetail saleDetail = SaleDetail.builder()
                        .game(requestGame)
                        .sale(sale)
                        .build();
                saleDetails.add(saleDetail);

                numberOfTicketsForAGame--;
            }
        }
        saleDetailRepository.persist((SaleDetailRepository) saleDetails.stream());
        LOGGER.info("Ticket has been sold");
        return new TicketResponse(
                "Ticket has been sold",
                ticketDetails
        );
    }

    private void generateTicket(BuyRequest buyRequest, Game requestGame, List<TicketDetail> ticketDetails) {
        String ticketCode = UUID.randomUUID().toString();
        LOGGER.info("ticket code generated");

        TicketDetail ticketDetail = TicketDetail.builder()
                .code(ticketCode)
                .price(requestGame.getPrice())
                .dayOfWeek(buyRequest.dayOfWeek())
                .dateOfGame(buyRequest.dateOfGame())
                .hour(buyRequest.hour())
                .nameOfGame(requestGame.getName())
                .build();
        ticketDetails.add(ticketDetail);
    }
}
