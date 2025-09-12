package com.walking.tree.parking.service.admin;

import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final ParkingSlotRepository slotRepository;

    public AdminService(ParkingSlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Transactional
    public ParkingSlot addSlot(ParkingSlot slot) {
        // Basic validation or business rules could go here
        slot.setStatus(slot.getStatus() != null ? slot.getStatus() : com.walking.tree.parking.entity.enums.SlotStatus.AVAILABLE);
        return slotRepository.save(slot);
    }
}
