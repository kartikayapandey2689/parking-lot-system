package com.walking.tree.parking.service.allocation;

import com.walking.tree.parking.entity.EntryGate;
import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.enums.VehicleType;

import java.util.Optional;

public interface SlotAllocationStrategy {
    Optional<ParkingSlot> findSlot(VehicleType type, EntryGate gate);
}