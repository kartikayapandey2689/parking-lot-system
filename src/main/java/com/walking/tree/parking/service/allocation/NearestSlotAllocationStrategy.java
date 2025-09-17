package com.walking.tree.parking.service.allocation;

import com.walking.tree.parking.entity.EntryGate;
import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.enums.SlotType;
import com.walking.tree.parking.entity.enums.VehicleType;
import com.walking.tree.parking.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("nearest")
public class NearestSlotAllocationStrategy implements SlotAllocationStrategy {

    private final ParkingSlotRepository slotRepository;

    public NearestSlotAllocationStrategy(ParkingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Optional<ParkingSlot> findSlot(VehicleType type, EntryGate gate) {
        SlotType slotType = SlotType.valueOf(type.name());
        List<ParkingSlot> candidates = slotRepository.findAvailableSlotsForTypeWithLock(slotType);
        if (candidates == null || candidates.isEmpty()) return Optional.empty();
        // If gate-specific distance available, refine using gate; currently return first (nearest global)
        return Optional.of(candidates.get(0));
    }
}
