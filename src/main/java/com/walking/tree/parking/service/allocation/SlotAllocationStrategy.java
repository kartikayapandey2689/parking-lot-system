package com.walking.tree.parking.service.allocation;

import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.enums.SlotType;

import java.util.Optional;

public interface SlotAllocationStrategy {
    Optional<ParkingSlot> findSlot(SlotType slotType);
}