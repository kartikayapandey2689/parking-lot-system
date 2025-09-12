package com.walking.tree.parking.repository;

import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.enums.SlotStatus;
import com.walking.tree.parking.entity.enums.SlotType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    // Lock the rows when searching to avoid double allocation
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ParkingSlot s WHERE s.type = :type AND s.status = :status ORDER BY s.distanceFromEntry ASC")
    List<ParkingSlot> findByTypeAndStatusForUpdate(@Param("type") SlotType type, @Param("status") SlotStatus status);

    List<ParkingSlot> findBySlotNumber(String slotNumber);
}
