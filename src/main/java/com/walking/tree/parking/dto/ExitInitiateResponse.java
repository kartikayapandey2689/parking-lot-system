package com.walking.tree.parking.dto;

import java.math.BigDecimal;

public class ExitInitiateResponse {
    private Long ticketId;
    private BigDecimal amount;

    public ExitInitiateResponse(Long ticketId, BigDecimal amount) {
        this.ticketId = ticketId;
        this.amount = amount;
    }

    public Long getTicketId() { return ticketId; }
    public BigDecimal getAmount() { return amount; }
}
