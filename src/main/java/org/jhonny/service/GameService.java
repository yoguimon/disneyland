package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.GameRequest;
import org.jhonny.exception.GameDtoNotFoundException;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
        gameRepository.persist(game);
        List<Schedule> scheduleList = new ArrayList<>();

        gameDto.scheduleRequests().forEach(scheduleRequest -> {
            Schedule newSchedule = Schedule.builder()
                    .dayOfWeek(scheduleRequest.dayOfWeek())
                    .openTime(scheduleRequest.openTime())
                    .closeTime(scheduleRequest.closeTime())
            .build();
            scheduleList.add(newSchedule);
        });
        scheduleRepository.persist(scheduleList);


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
