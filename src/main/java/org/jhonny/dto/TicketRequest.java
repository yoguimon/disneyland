package org.jhonny.dto;

import java.util.List;

public record TicketRequest(Long buyerId, Long ticketOfficeId, List<BuyRequest> buyRequests) { }
