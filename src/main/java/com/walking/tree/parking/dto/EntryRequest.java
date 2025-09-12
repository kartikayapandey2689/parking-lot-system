package com.walking.tree.parking.dto;

import com.walking.tree.parking.entity.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EntryRequest {
    @NotBlank
    private String plate;

    @NotNull
    private VehicleType type;

    // getters/setters
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
}
