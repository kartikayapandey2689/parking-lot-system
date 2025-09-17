package com.walking.tree.parking.service;

import com.walking.tree.parking.dto.*;
import com.walking.tree.parking.entity.EntryGate;
import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.entity.Payment;
import com.walking.tree.parking.entity.Ticket;
import com.walking.tree.parking.entity.enums.PaymentStatus;
import com.walking.tree.parking.entity.enums.SlotStatus;
import com.walking.tree.parking.entity.enums.TicketStatus;
import com.walking.tree.parking.exception.NotFoundException;
import com.walking.tree.parking.repository.EntryGateRepository;
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
    private final EntryGateRepository gateRepository;

    public ParkingService(TicketRepository ticketRepository,
                          ParkingSlotRepository slotRepository,
                          SlotAllocationStrategy allocationStrategy,
                          PaymentService paymentService, EntryGateRepository gateRepository) {
        this.ticketRepository = ticketRepository;
        this.slotRepository = slotRepository;
        this.allocationStrategy = allocationStrategy;
        this.paymentService = paymentService;
        this.gateRepository = gateRepository;
    }

    /**
     * Park vehicle: transactional to ensure allocation and ticket creation are atomic.
     */
    @Transactional
    public EntryResponse parkVehicle(EntryRequest request) {
        // Prevent duplicate active tickets
        ticketRepository.findByVehiclePlateAndStatus(request.getPlate(), TicketStatus.ACTIVE)
                .ifPresent(t -> { throw new IllegalStateException("Vehicle already parked with ticket: " + t.getId()); });
        EntryGate gate = gateRepository.findById(request.getGateId()).orElseThrow(() -> new NotFoundException("Gate not found"));

        // Find slot (pessimistic lock inside strategy/repo prevents duplicate allocation)
        ParkingSlot slot = allocationStrategy.findSlot(request.getType(), gate)
                .orElseThrow(() -> new IllegalStateException("No available slot for type: " + request.getType()));

        // Reserve slot by changing status
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepository.save(slot);

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setVehiclePlate(request.getPlate());
        ticket.setVehicleType(request.getType());
        ticket.setSlot(slot);
        ticket.setEntryGate(gate);
        ticket.setEntryTime(Instant.now());
        ticket.setStatus(TicketStatus.ACTIVE);
        ticketRepository.save(ticket);

        return new EntryResponse(ticket.getId(), slot.getSlotNumber(), ticket.getEntryTime());
    }
    @Transactional
    public ExitInitiateResponse initiateExit(ExitRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId()).orElseThrow(() -> new NotFoundException("Ticket not found"));
        if (ticket.getStatus() != TicketStatus.ACTIVE) throw new IllegalStateException("Ticket not active");

        Instant exitTime = Instant.now();
        ticket.setExitTime(exitTime);
        BigDecimal amount = PricingCalculator.calculate(ticket.getVehicleType(), java.time.Duration.between(ticket.getEntryTime(), exitTime).toMinutes());
        ticket.setAmount(amount);
        ticket.setStatus(TicketStatus.PENDING_PAYMENT);
        ticketRepository.save(ticket);

        return new ExitInitiateResponse(ticket.getId(), amount);
    }
    @Transactional
    public ReceiptResponse payAndExit(PaymentRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId()).orElseThrow(() -> new NotFoundException("Ticket not found"));
        if (ticket.getStatus() != TicketStatus.PENDING_PAYMENT) throw new IllegalStateException("Ticket not pending payment");

        // Charge
        paymentService.charge(request.getCardNumber(), ticket.getAmount());

        // Persist payment - omitted PaymentRepository save here for brevity
        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setAmount(ticket.getAmount());
        payment.setTimestamp(Instant.now());
        payment.setStatus(PaymentStatus.SUCCESS);
        // paymentRepository.save(payment); // you can autowire and save

        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.save(ticket);

        ParkingSlot slot = ticket.getSlot();
        if (slot == null) throw new IllegalStateException("Ticket has no slot");
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(slot);

        return new ReceiptResponse(ticket.getId(), ticket.getAmount(), ticket.getExitTime());
    }
}
