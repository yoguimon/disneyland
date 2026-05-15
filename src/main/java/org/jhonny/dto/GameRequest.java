package org.jhonny.dto;

import java.math.BigDecimal;
import java.util.List;

public record GameRequest(String name, String description, BigDecimal price, List<Long> schedulesIds, List<Long> employeesIds) { }
