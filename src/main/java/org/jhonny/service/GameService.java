package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.GameRequest;
import org.jhonny.exception.EmployeeNotFoundException;
import org.jhonny.exception.GameDtoNotFoundException;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.exception.ScheduleNotFoundException;
import org.jhonny.models.Employee;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class GameService{

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    @Inject
    public GameService(GameRepository gameRepository,
                       ScheduleRepository scheduleRepository,
                       EmployeeRepository employeeRepository) {

        this.gameRepository = gameRepository;
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void addNewGame(GameRequest gameDto) {
        if(Objects.isNull(gameDto)){
            LOGGER.error("GameDto is null");
            throw new GameDtoNotFoundException("GameDto not found");
        }

        Game game = new Game();
        game.setName(gameDto.name());
        game.setDescription(gameDto.description());
        game.setPrice(gameDto.price());

        if (gameDto.schedulesIds()==null || gameDto.schedulesIds().isEmpty()) {
            LOGGER.error("SchedulesIds is null o empty");
            throw new ScheduleNotFoundException("SchedulesIds is null or empty");
        }
        List<Schedule> schedules = scheduleRepository.list("id IN ?1", gameDto.schedulesIds());
        game.setSchedules(schedules);

        if (gameDto.employeesIds() == null || gameDto.employeesIds().isEmpty()) {
            LOGGER.error("EmployeesIds is null o empty");
            throw new EmployeeNotFoundException("EmployeesIds is null or empty");
        }
        List<Employee> employees = employeeRepository.list("id IN ?1", gameDto.employeesIds());
        game.setEmployees(employees);
        gameRepository.persist(game);

        LOGGER.info("New Game added{}",  game);
    }
    public Game getGame(Long id){
        Game requestGame = gameRepository.findById(id);

        if(Objects.isNull(requestGame)) {
            LOGGER.error("Game not found");
            throw new GameNotFoundException("Game not found");
        }
        return requestGame;
    }
}
