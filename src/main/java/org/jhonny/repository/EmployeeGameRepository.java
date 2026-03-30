package org.jhonny.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.exception.EmployeePersistenceException;
import org.jhonny.exception.GamePersistenceException;
import org.jhonny.models.EmployeeGame;
import org.jhonny.models.Game;
import org.jhonny.models.User;
import org.jhonny.utils.EmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class EmployeeGameRepository implements PanacheRepository<EmployeeGame>, EmployeeRepository {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeGameRepository.class);

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Inject
    public EmployeeGameRepository(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        try{

            User user = User.builder()
                    .username(employeeRequest.email())
                    .password(employeeRequest.lastName())
                    .build();
            userRepository.persist(user);

            LOGGER.info("Added user {}",  user);

            /// we can get this from cache
            Game game = gameRepository.findById(employeeRequest.gameId());
            if (game == null){
                LOGGER.error("Game with id {} not found", employeeRequest.gameId());
                throw new GamePersistenceException("Game not found");
            }

            EmployeeGame employeeGame = EmployeeGame.builder()
                    .ci(employeeRequest.ci())
                    .firstName(employeeRequest.firstName())
                    .lastName(employeeRequest.lastName())
                    .email(employeeRequest.email())
                    .type(employeeRequest.typePerson())
                    .user(user)
                    .game(game)
                    .build();
            this.persist(employeeGame);

            LOGGER.info("Added employee {}", employeeGame);


            return new EmployeeResponse(
                    "New employee added successfully",
                    employeeGame
            );

        }catch(Exception e){

            LOGGER.error("Employee not added successfully");
            throw new EmployeePersistenceException("Employee not added successfully");
        }
    }

    /// 6
    public List<EmployeeGame> getListOfEmployeesWithHisResponsableGame(){
        return findAll().stream().toList();
    }

    @Override
    public EmployeeType getType() {
        return EmployeeType.EMPLOYEE;
    }
}
