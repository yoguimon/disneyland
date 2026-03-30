package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.GameRequest;
import org.jhonny.exception.GameDtoNotFoundException;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.exception.GamePersistenceException;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class GameService{

    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final ScheduleRepository scheduleRepository;

    @Inject
    public GameService(GameRepository gameRepository,
                       ScheduleRepository scheduleRepository) {

        this.gameRepository = gameRepository;
        this.scheduleRepository = scheduleRepository;

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

        /// here we need to use cache instead of calling  each time to database
        gameDto.schedulesIds().forEach(id -> {
            Schedule newSchedule = scheduleRepository.findById(id);
            game.getSchedules().add(newSchedule);
        });

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
