package org.jhonny.dto;

import lombok.Data;
import org.jhonny.utils.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
public class BuyRequestDto {
    private Long idBuyer;
    private double price;
    private DayOfWeek dayOfWeek;
    private LocalDate date;
    private LocalTime hour;
    private int amount;
    private Long idGame;
}
