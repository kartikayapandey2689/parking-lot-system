package com.walking.tree.parking.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class ReceiptResponse {
    private Long ticketId;
    private BigDecimal amount;
    private Instant exitTime;

    public ReceiptResponse(Long ticketId, BigDecimal amount, Instant exitTime) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.exitTime = exitTime;
    }

    public Long getTicketId() { return ticketId; }
    public BigDecimal getAmount() { return amount; }
    public Instant getExitTime() { return exitTime; }
}
