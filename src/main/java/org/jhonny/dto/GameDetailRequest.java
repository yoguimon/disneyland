package org.jhonny.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GameDetailRequest(int amount, Long scheduleId, LocalDate dateOfGame) {
}
