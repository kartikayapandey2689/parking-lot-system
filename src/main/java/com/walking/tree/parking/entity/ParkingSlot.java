package com.walking.tree.parking.entity;

import com.walking.tree.parking.entity.enums.SlotStatus;
import com.walking.tree.parking.entity.enums.SlotType;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_slot", uniqueConstraints = @UniqueConstraint(columnNames = {"floor_id", "slot_number"}))
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SLOT_NUMBER")
    private String slotNumber; // e.g., F1-C-23

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }

    @Enumerated(EnumType.STRING)
    private SlotType slotType;

    @Enumerated(EnumType.STRING)
    private SlotStatus status = SlotStatus.AVAILABLE;

    @Column(name = "DISTANCE_FROM_ENTRY")
    private Integer distanceFromEntry = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    private Floor floor;


    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public SlotStatus getStatus() { return status; }
    public void setStatus(SlotStatus status) { this.status = status; }
    public Integer getDistanceFromEntry() { return distanceFromEntry; }
    public void setDistanceFromEntry(Integer distanceFromEntry) { this.distanceFromEntry = distanceFromEntry; }
    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
}
