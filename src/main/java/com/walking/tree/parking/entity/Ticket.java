package com.walking.tree.parking.entity;

import com.walking.tree.parking.entity.enums.TicketStatus;
import com.walking.tree.parking.entity.enums.VehicleType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "ticket", indexes = {@Index(name = "idx_ticket_vehicle_active", columnList = "vehicle_plate, status")})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehiclePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParkingSlot slot;

    @ManyToOne
    private EntryGate entryGate;

    private Instant entryTime;

    private Instant exitTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.ACTIVE;

    private BigDecimal amount;

    // getters/setters
    public Long getId() { return id; }
    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public ParkingSlot getSlot() { return slot; }
    public void setSlot(ParkingSlot slot) { this.slot = slot; }
    public Instant getEntryTime() { return entryTime; }
    public void setEntryTime(Instant entryTime) { this.entryTime = entryTime; }
    public Instant getExitTime() { return exitTime; }
    public void setExitTime(Instant exitTime) { this.exitTime = exitTime; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public EntryGate getEntryGate() { return entryGate; }
    public void setEntryGate(EntryGate entryGate) { this.entryGate = entryGate; }
}
