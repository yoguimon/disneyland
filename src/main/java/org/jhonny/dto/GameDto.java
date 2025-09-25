package org.jhonny.dto;

import lombok.Data;
import org.jhonny.models.Employee;
import org.jhonny.models.Schedule;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
public class GameDto {
    private String name;

    private String description;

    private double price;

    private Set<Long> employeesIds;

    private Set<Long> schedulesIds;
}
