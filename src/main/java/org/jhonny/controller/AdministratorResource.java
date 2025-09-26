package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.service.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/v1/administrators")
@RequestScoped
public class AdministratorResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(AdministratorResource.class);

    private final AdministratorService administratorService;

    @Inject
    public AdministratorResource(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @POST
    @Path("/persons")
    public Response addPerson(PersonRequest person) {

        try{
            LOGGER.info("Registering person {}",  person);
            PersonResponse newPerson = administratorService.addPerson(person);
            return Response.status(Response.Status.CREATED)
                    .entity(newPerson)
                    .build();

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
