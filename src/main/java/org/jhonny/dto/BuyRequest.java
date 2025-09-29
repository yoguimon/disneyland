package org.jhonny.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record BuyRequest(DayOfWeek dayOfWeek, LocalDate dateOfGame, LocalTime hour, int amount, Long gameId) { }
