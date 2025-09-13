package com.walking.tree.parking.controller;

import com.walking.tree.parking.dto.*;
import com.walking.tree.parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) { this.parkingService = parkingService; }

    @PostMapping("/entry")
    public ResponseEntity<EntryResponse> enter(@Valid @RequestBody EntryRequest req) {
        EntryResponse res = parkingService.parkVehicle(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/exit/initiate")
    public ResponseEntity<ExitInitiateResponse> exitInitiate(@Valid @RequestBody ExitRequest req) {
        return ResponseEntity.ok(parkingService.initiateExit(req));
    }

    @PostMapping("/exit/pay")
    public ResponseEntity<ReceiptResponse> payExit(@Valid @RequestBody PaymentRequest req) {
        return ResponseEntity.ok(parkingService.payAndExit(req));
    }
}
