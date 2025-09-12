package com.walking.tree.parking.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., F1

    private Integer level;

    @OneToMany(mappedBy = "floor")
    private List<ParkingSlot> slots;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public List<ParkingSlot> getSlots() { return slots; }
    public void setSlots(List<ParkingSlot> slots) { this.slots = slots; }
}
