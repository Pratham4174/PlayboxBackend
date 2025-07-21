package com.playbox.arenaowner.controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playbox.arenaowner.dto.OTPRequestDto;
import com.playbox.arenaowner.dto.OTPVerifyDto;
import com.playbox.arenaowner.entity.ArenaOwner;
import com.playbox.arenaowner.service.ArenaOwnerService;

@RestController
@RequestMapping("/api/arena-owners")
public class ArenaOwnerController {

    @Autowired
    private ArenaOwnerService service;

    @GetMapping("/by-phone")
    public ResponseEntity<?> getOwnerByPhoneNumber(@RequestParam String phoneNumber) {
        return service.getOwnerByPhoneNumber(phoneNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found"));
    }

    @PostMapping("/register")
    public ArenaOwner register(@RequestBody ArenaOwner owner) {
        return service.registerOwner(owner);
    }

    @GetMapping
    public List<ArenaOwner> getAllOwners() {
        return service.getAllOwners();
    }

    @GetMapping("/{id}")
    public ArenaOwner getOwner(@PathVariable Long id) {
        return service.getOwnerById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return service.login(email, password)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }



@PostMapping("/send-otp")
public ResponseEntity<String> sendOtp(@RequestBody OTPRequestDto request) {
    service.sendOtp(request.getPhoneNumber());
  return ResponseEntity.ok("OTP sent");
}

@PostMapping("/verify-otp")
public ResponseEntity<String> verifyOtp(@RequestBody OTPVerifyDto request) {
  boolean success = service.verifyOtp(request.getPhoneNumber(), request.getOtp());
  return success ? ResponseEntity.ok("OTP verified") : ResponseEntity.status(401).body("Invalid OTP");
}

}
