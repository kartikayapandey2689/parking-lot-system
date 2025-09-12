package com.walking.tree.parking.repository;

import com.walking.tree.parking.entity.Ticket;
import com.walking.tree.parking.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehiclePlateAndStatus(String vehiclePlate, TicketStatus status);
}
