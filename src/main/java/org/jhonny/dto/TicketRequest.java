package org.jhonny.dto;

import java.util.List;

public record TicketRequest(Long clientId, Long ticketOfficeId, int amount, double total, List<BuyRequest> buyRequests) { }
