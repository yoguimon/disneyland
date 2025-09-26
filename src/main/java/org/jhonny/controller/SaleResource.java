package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.dto.TicketRequest;
import org.jhonny.dto.TicketResponse;
import org.jhonny.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/v1/sales")
@RequestScoped
public class SaleResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(SaleResource.class);

    private final SaleService saleService;

    @Inject
    public SaleResource(SaleService saleService) {
        this.saleService = saleService;
    }

    @POST
    public Response sellTicket(TicketRequest requestTicket) {

        try{
            LOGGER.info("Registering person");
            TicketResponse TicketResponse = saleService.sellTicket(requestTicket);
            return Response.status(Response.Status.CREATED)
                    .entity(TicketResponse)
                    .build();

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
