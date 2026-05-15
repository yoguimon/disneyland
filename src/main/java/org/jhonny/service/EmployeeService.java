package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.exception.EmployeePersistenceException;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.models.Employee;
import org.jhonny.models.EmployeeAdmin;
import org.jhonny.models.Game;
import org.jhonny.models.User;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.UserRepository;
import org.jhonny.strategy.EmployeeMapperStrategy;
import org.jhonny.utils.EmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final Instance<EmployeeMapperStrategy<?>> strategies;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository,
                           GameRepository gameRepository,
                           UserRepository userRepository,
                           @Any Instance<EmployeeMapperStrategy<?>> strategies) {
        this.employeeRepository = employeeRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.strategies = strategies;
    }

    @Transactional
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        try{
            User user = User.builder()
                    .username(employeeRequest.getEmail())
                    .password(employeeRequest.getCi()) /// we can generate a random password and send to the user email
                    .build();
            userRepository.persist(user);

            LOGGER.info("Added user {}",  user);

            Employee employee = strategies.stream()
                    .filter(strategy ->
                            strategy.supportsRequest(employeeRequest))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Unsupported type"))
                    .map(employeeRequest);

            if (employeeRequest.getGameIds() == null || employeeRequest.getGameIds().isEmpty()){
                LOGGER.error("Employee needs at leats one game to take over");
                throw new GameNotFoundException("Games not found associate to user");
            }
            /// we can get this from cache??
            List<Game> games = gameRepository.list("id IN ?1", employeeRequest.getGameIds());
            employee.setGames(games);
            employee.setUser(user);

            employeeRepository.persist(employee);
            LOGGER.info("Added employee {}, games to take over: {}", employee.getFirstName(), employee.getGames().size());

            return new EmployeeResponse(
                    "New employee added successfully",
                    employee.getFirstName() + " " + employee.getLastName()
            );

        }catch(Exception e){

            LOGGER.error("Employee not added successfully", e);
            /// the error should also throw a exception e
            throw new EmployeePersistenceException("Employee not added successfully");
        }
    }

    public void addRootEmployee() {
        // Add root user
    }
}
