package com.walking.tree.parking.service.allocation;

import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.enums.SlotStatus;
import com.walking.tree.parking.entity.enums.SlotType;
import com.walking.tree.parking.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("nearestAllocation")
public class NearestSlotAllocationStrategy implements SlotAllocationStrategy {

    private final ParkingSlotRepository slotRepository;

    public NearestSlotAllocationStrategy(ParkingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Optional<ParkingSlot> findSlot(SlotType slotType) {
        // This method uses repo method with PESSIMISTIC_WRITE lock to avoid double allocation
        List<ParkingSlot> candidates = slotRepository.findByTypeAndStatusForUpdate(slotType, SlotStatus.AVAILABLE);
        if (candidates == null || candidates.isEmpty()) return Optional.empty();
        return Optional.of(candidates.get(0));
    }
}
