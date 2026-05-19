package org.jhonny.dto;

import java.util.Map;

public record SaleRequest(Long clientId, Long employeeId, Long ticketOfficeId, int TotalAmount,
                          double totalPrice, Map<String, GameDetailRequest> gameDetails) { }
