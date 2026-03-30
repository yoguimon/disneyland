package org.jhonny.dto;

import java.time.LocalTime;

public record ScheduleRequest(LocalTime openTime, LocalTime closeTime, Long gameId) {
}
