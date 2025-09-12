package com.walking.tree.parking.service;

import com.walking.tree.parking.dto.EntryRequest;
import com.walking.tree.parking.dto.EntryResponse;
import com.walking.tree.parking.dto.ExitRequest;
import com.walking.tree.parking.dto.ReceiptResponse;
import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.Ticket;
import com.walking.tree.parking.entity.enums.SlotStatus;
import com.walking.tree.parking.entity.enums.TicketStatus;
import com.walking.tree.parking.entity.enums.VehicleType;
import com.walking.tree.parking.exception.NotFoundException;
import com.walking.tree.parking.repository.ParkingSlotRepository;
import com.walking.tree.parking.repository.TicketRepository;
import com.walking.tree.parking.service.allocation.SlotAllocationStrategy;
import com.walking.tree.parking.util.PricingCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class ParkingService {

    private final TicketRepository ticketRepository;
    private final ParkingSlotRepository slotRepository;
    private final SlotAllocationStrategy allocationStrategy;
    private final PaymentService paymentService;

    public ParkingService(TicketRepository ticketRepository,
                          ParkingSlotRepository slotRepository,
                          SlotAllocationStrategy allocationStrategy,
                          PaymentService paymentService) {
        this.ticketRepository = ticketRepository;
        this.slotRepository = slotRepository;
        this.allocationStrategy = allocationStrategy;
        this.paymentService = paymentService;
    }

    /**
     * Park vehicle: transactional to ensure allocation and ticket creation are atomic.
     */
    @Transactional
    public EntryResponse parkVehicle(EntryRequest request) {
        // Prevent duplicate active tickets
        ticketRepository.findByVehiclePlateAndStatus(request.getPlate(), TicketStatus.ACTIVE)
                .ifPresent(t -> { throw new IllegalStateException("Vehicle already parked with ticket: " + t.getId()); });

        // Find slot (pessimistic lock inside strategy/repo prevents duplicate allocation)
        ParkingSlot slot = allocationStrategy.findSlot(convertToSlotType(request.getType()))
                .orElseThrow(() -> new IllegalStateException("No available slot for type: " + request.getType()));

        // Reserve slot by changing status
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepository.save(slot);

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setVehiclePlate(request.getPlate());
        ticket.setVehicleType(convertToVehicleType(request.getType()));
        ticket.setSlot(slot);
        ticket.setEntryTime(Instant.now());
        ticket.setStatus(TicketStatus.ACTIVE);
        ticketRepository.save(ticket);

        return new EntryResponse(ticket.getId(), slot.getSlotNumber(), ticket.getEntryTime());
    }

    @Transactional
    public ReceiptResponse exitVehicle(ExitRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new NotFoundException("Ticket not found: " + request.getTicketId()));

        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket is not active");
        }

        Instant exitTime = Instant.now();
        long minutes = java.time.Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        BigDecimal amount = PricingCalculator.calculate(ticket.getVehicleType(), minutes);

        // charge (if fails throw PaymentException and rollback)
        String cardNumber = request.getPaymentInfo() != null ? request.getPaymentInfo().getCardNumber() : null;
        paymentService.charge(cardNumber, amount);

        // mark ticket paid and free slot
        ticket.setExitTime(exitTime);
        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.save(ticket);

        ParkingSlot slot = ticket.getSlot();
        if (slot == null) throw new IllegalStateException("Ticket slot missing");
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(slot);

        return new ReceiptResponse(ticket.getId(), amount, exitTime);
    }

    // helper
    private com.walking.tree.parking.entity.enums.SlotType convertToSlotType(VehicleType vt) {
        return com.walking.tree.parking.entity.enums.SlotType.valueOf(vt.name());
    }

    private VehicleType convertToVehicleType(VehicleType vt) {
        return vt;
    }
}
