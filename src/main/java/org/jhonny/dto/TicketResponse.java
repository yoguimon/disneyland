package org.jhonny.dto;

import org.jhonny.models.TicketDetails;

import java.util.List;

public record TicketResponse(String message, List<TicketDetails> ticketDetails) {
}
