package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jhonny.dto.PersonDto;
import org.jhonny.dto.Response;
import org.jhonny.service.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministratorResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(this.getClass());

    private final AdministratorService administratorService;

    @Inject
    public AdministratorResource(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @POST
    @Path("/person")
    public Response registerPerson(PersonDto person) {

        LOGGER.info("Registering person {}",  person);
        return administratorService.addPerson(person);
    }
}
