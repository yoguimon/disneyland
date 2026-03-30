package org.jhonny.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetail {
    private String code;
    private BigDecimal price;
    private DayOfWeek dayOfWeek;
    private LocalDate dateOfGame;
    private LocalTime hour;
    private String nameOfGame;
}
