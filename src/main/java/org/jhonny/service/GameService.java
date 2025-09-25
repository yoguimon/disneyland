package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.jhonny.dto.GameDto;
import org.jhonny.dto.Response;
import org.jhonny.models.Employee;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class GameService{

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Response addNewGame(GameDto gameDto) {
        try{
            if(Objects.isNull(gameDto)){
                new Response("Invalid game data");
            }

            Game game = new Game();
            game.setName(gameDto.getName());
            game.setDescription(gameDto.getDescription());
            game.setPrice(gameDto.getPrice());

            gameDto.getEmployeesIds().forEach(id -> {
                //id
                Employee newEmployee = gameRepository.find(Employee.class, id);
                game.getEmployees().add(newEmployee);
                newEmployee.getGames().add(game);
            });

            gameDto.getSchedulesIds().forEach(id -> {
               Schedule newSchedule = gameRepository.find(Schedule.class, id);
               game.getSchedules().add(newSchedule);
               newSchedule.getGames().add(game);
            });

            gameRepository.save(game);

            LOGGER.info("New Game added{}",  game);

            return new Response("success");

        }catch(Exception e){
            LOGGER.error("Error adding New Game",e);
            return new Response("error");

        }
    }
    public List<Game> getGames(){
        try{
            return gameRepository.findAll();
        }catch(Exception e){
            LOGGER.error("Error finding Games",e);
            throw new RuntimeException("Error finding Games");
        }
    }
}
