package org.jhonny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jhonny.dto.ScheduleRequest;
import org.jhonny.exception.GameNotFoundException;
import org.jhonny.exception.ScheduleNotFoundException;
import org.jhonny.models.Game;
import org.jhonny.models.Schedule;
import org.jhonny.repository.GameRepository;
import org.jhonny.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ScheduleService{

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;
    private final GameRepository gameRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository, GameRepository gameRepository) {
        this.scheduleRepository = scheduleRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void addSchedule(ScheduleRequest scheduleRequest) {
        if(Objects.isNull(scheduleRequest)) {
            LOGGER.error("The schedule is null");
            throw new ScheduleNotFoundException("The schedule is null");

        }
        Game game = gameRepository.findById(scheduleRequest.gameId());
        if (game == null) {
            LOGGER.error("Game with id {} not found", scheduleRequest.gameId());
            throw new GameNotFoundException("Game not found");
        }
        Schedule schedule = Schedule.builder()
                .openTime(scheduleRequest.openTime())
                .closeTime(scheduleRequest.closeTime())
                .game(game)
                .build();

        scheduleRepository.persist(schedule);
        LOGGER.info("Schedule added successfully");
    }

    public List<Schedule> getAll(){
        /// we can get from cache
        return scheduleRepository.findAll().stream().toList();
    }
}
