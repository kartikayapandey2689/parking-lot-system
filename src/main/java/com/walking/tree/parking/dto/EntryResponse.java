package com.walking.tree.parking.dto;

import java.time.Instant;

public class EntryResponse {
    private Long ticketId;
    private String slotNumber;
    private Instant entryTime;

    public EntryResponse(Long ticketId, String slotNumber, Instant entryTime) {
        this.ticketId = ticketId;
        this.slotNumber = slotNumber;
        this.entryTime = entryTime;
    }
    // getters
    public Long getTicketId() { return ticketId; }
    public String getSlotNumber() { return slotNumber; }
    public Instant getEntryTime() { return entryTime; }
}