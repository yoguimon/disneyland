package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.BuyRequest;
import org.jhonny.dto.GameDetailRequest;
import org.jhonny.dto.TicketDetail;
import org.jhonny.dto.SaleRequest;
import org.jhonny.dto.TicketResponse;
import org.jhonny.models.Client;
import org.jhonny.models.Employee;
import org.jhonny.models.Game;
import org.jhonny.models.Sale;
import org.jhonny.models.SaleDetail;
import org.jhonny.models.Schedule;
import org.jhonny.models.TicketOffice;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.repository.SaleDetailRepository;
import org.jhonny.repository.SaleRepository;
import org.jhonny.repository.ScheduleRepository;
import org.jhonny.repository.TicketOfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class SaleService {
    private final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    private final TicketOfficeRepository ticketOfficeRepository;
    private final SaleRepository saleRepository;
    private final GameService gameService;
    private final ClientService clientService;
    private final SaleDetailRepository saleDetailRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;

    @Inject
    public SaleService(TicketOfficeRepository ticketOfficeRepository,
                       SaleRepository saleRepository, GameService gameService,
                       ClientService clientService, SaleDetailRepository saleDetailRepository,
                       EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository) {
        this.ticketOfficeRepository = ticketOfficeRepository;
        this.saleRepository = saleRepository;
        this.gameService = gameService;
        this.clientService = clientService;
        this.saleDetailRepository = saleDetailRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public TicketResponse addSale(SaleRequest saleRequest){
        /// Refactor this
        Client client = clientService.getClient(saleRequest.clientId());
        Employee employee = employeeRepository.findById(saleRequest.employeeId());
        /// we can get it from cache
        TicketOffice ticketOffice = ticketOfficeRepository.findById(saleRequest.ticketOfficeId());

        Sale sale = Sale.builder()
                .client(client)
                .employee(employee)
                .ticketOffice(ticketOffice)
                .amount(saleRequest.TotalAmount())
                .total(saleRequest.totalPrice())
                .createAt(LocalDate.now())
                .build();
        saleRepository.persist(sale);

        //List<TicketDetail> ticketDetails = new ArrayList<>();
        List<SaleDetail> saleDetails = new ArrayList<>();

        /// here you can refactor, use cache, etc.
        Map<String, GameDetailRequest> gameDetailsRequest = saleRequest.gameDetails();
        for(Map.Entry<String, GameDetailRequest> input : gameDetailsRequest.entrySet()) {
            int amountAux = input.getValue().amount();
            while(amountAux<1){
                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setSale(sale);
                Game game = gameService.getGame(Long.parseLong(input.getKey()));
                saleDetail.setGame(game);
                Schedule schedule = scheduleRepository.findById(input.getValue().scheduleId());
                saleDetail.setSchedule(schedule);
                saleDetail.setPrice(game.getPrice());
                saleDetail.setDateOfGame(input.getValue().dateOfGame());
                saleDetail.setCreateAt(LocalDate.now());
                saleDetails.add(saleDetail);
                amountAux--;
            }
        }
        /// this is valid? we need to confirm as fast as possible
        sale.setSaleDetails(saleDetails);
        saleRepository.persist(sale);
        LOGGER.info("Sale has been added to the database");

        /// we need to generate ticket

        LOGGER.info("Ticket has been sold");
        return new TicketResponse(
                "Ticket has been sold",
                null
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
