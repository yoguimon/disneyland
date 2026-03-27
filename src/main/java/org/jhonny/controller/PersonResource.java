package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;
import org.jhonny.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/v1/persons")
@RequestScoped
public class PersonResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(PersonResource.class);

    private final PersonService personService;

    @Inject
    public PersonResource(PersonService administratorService) {
        this.personService = administratorService;
    }

    @POST
    public Response addPerson(PersonRequest person) {

        try{
            LOGGER.info("Registering person {}",  person);
            PersonResponse newPerson = personService.addPerson(person);
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
