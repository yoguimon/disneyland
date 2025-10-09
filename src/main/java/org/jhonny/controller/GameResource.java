package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.dto.GameRequest;
import org.jhonny.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("api/v1/games")
@RequestScoped
public class GameResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(GameResource.class);

    private final GameService gameService;

    @Inject
    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @POST
    public Response addGame(GameRequest game) {

        try{
            LOGGER.info("Registering game {}", game);
            gameService.addNewGame(game);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("message", "Game added successfully"))
                    .build();

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
