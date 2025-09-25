package org.jhonny.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestTicket {
    private List<BuyRequestDto> buyRequests;
}
