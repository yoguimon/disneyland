package org.jhonny.dto;

import org.jhonny.models.TicketDetail;

import java.util.List;

public record TicketResponse(String message, List<TicketDetail> ticketDetails) {
}
