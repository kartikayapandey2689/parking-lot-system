package com.walking.tree.parking.dto;

import jakarta.validation.constraints.NotNull;

public class ExitRequest {
    @NotNull
    private Long ticketId;

    // getters/setters
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
