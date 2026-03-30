package org.jhonny.dto;

import java.util.List;

public record TicketResponse(String message, List<TicketDetail> ticketDetails) {
}
