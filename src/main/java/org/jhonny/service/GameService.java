package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.GameRequest;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.models.Employees;
import org.jhonny.models.Games;
import org.jhonny.models.Schedules;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class GameService{

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;

    @Inject
    public GameService(GameRepository gameRepository, EmployeeRepository employeeRepository,
                       ScheduleRepository scheduleRepository) {

        this.gameRepository = gameRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;

    }

    @Transactional
    public void addNewGame(GameRequest gameDto) {
        try{
            if(Objects.isNull(gameDto)){
                LOGGER.error("Game not found");
                throw new GameNotFoundException("Game not found");
            }

            Games game = new Games();
            game.setName(gameDto.name());
            game.setDescription(gameDto.description());
            game.setPrice(gameDto.price());

            Employees newEmployee = employeeRepository.findById(gameDto.employeeId());
            game.setEmployee(newEmployee);

            gameDto.schedulesIds().forEach(id -> {
                Schedules newSchedule = scheduleRepository.findById(id);
               game.getSchedules().add(newSchedule);
               newSchedule.getGames().add(game);
            });

            gameRepository.persist(game);

            LOGGER.info("New Game added{}",  game);


        }catch(Exception e){
            LOGGER.error("Error adding New Game",e);

        }
    }
}
