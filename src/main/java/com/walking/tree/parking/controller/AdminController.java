package com.walking.tree.parking.controller;


import com.walking.tree.parking.entity.ParkingSlot;
import com.walking.tree.parking.service.admin.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) { this.adminService = adminService; }

    @PostMapping("/slot")
    public ResponseEntity<ParkingSlot> addSlot(@RequestBody ParkingSlot slot) {
        ParkingSlot saved = adminService.addSlot(slot);
        return ResponseEntity.ok(saved);
    }
}
