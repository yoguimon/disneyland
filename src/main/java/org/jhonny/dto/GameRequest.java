package org.jhonny.dto;

import java.math.BigDecimal;
import java.util.Set;

public record GameRequest(String name, String description, BigDecimal price, Long employeeId, Set<Long> schedulesIds) { }
