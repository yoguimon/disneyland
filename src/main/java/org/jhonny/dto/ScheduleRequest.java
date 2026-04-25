package org.jhonny.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleRequest(LocalTime openTime, LocalTime closeTime, DayOfWeek dayOfWeek) {
}
