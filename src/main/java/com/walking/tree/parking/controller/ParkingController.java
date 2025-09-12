package com.walking.tree.parking.controller;

import com.walking.tree.parking.dto.EntryRequest;
import com.walking.tree.parking.dto.EntryResponse;
import com.walking.tree.parking.dto.ExitRequest;
import com.walking.tree.parking.dto.ReceiptResponse;
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

    @PostMapping("/exit")
    public ResponseEntity<ReceiptResponse> exit(@Valid @RequestBody ExitRequest req) {
        ReceiptResponse res = parkingService.exitVehicle(req);
        return ResponseEntity.ok(res);
    }
}
