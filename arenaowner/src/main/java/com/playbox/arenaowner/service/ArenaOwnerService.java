package com.playbox.arenaowner.service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playbox.arenaowner.entity.ArenaOwner;
import com.playbox.arenaowner.repo.ArenaOwnerRepository;

@Service
public class ArenaOwnerService {
private Map<String, String> otpStore = new ConcurrentHashMap<>();
    @Autowired
    private ArenaOwnerRepository repository;
    @Autowired
    private ArenaOwnerRepository arena;


    // Register a new arena owner
    public ArenaOwner registerOwner(ArenaOwner owner) {
        return repository.save(owner);
    }

    // Get all arena owners
    public List<ArenaOwner> getAllOwners() {
        return repository.findAll();
    }

    // Get a specific arena owner by ID
    public ArenaOwner getOwnerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Login logic (basic)
    public Optional<ArenaOwner> login(String email, String password) {
        Optional<ArenaOwner> owner = repository.findByEmail(email);
        if (owner.isPresent() && owner.get().getPassword().equals(password)) {
            return owner;
        }
        return Optional.empty();
    }

    public Optional<ArenaOwner> getOwnerByPhoneNumber(String phoneNumber) {
        return repository.findByPhone(phoneNumber);
    }

    public void sendOtp(String phoneNumber) {
        // if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
        //     throw new IllegalArgumentException("Phone number is required");
        // }
       System.out.println("Sending OTP to: " + phoneNumber);
        String otp = "1234"; // Fake OTP for now
        otpStore.put(phoneNumber, otp);
    
        arena.findByPhone(phoneNumber).orElseGet(() -> {
          ArenaOwner owner  = new ArenaOwner();
          owner.setPhone(phoneNumber);
          return arena.save(owner);
        });
    
        System.out.println("Mock OTP sent: " + otp);
      }
    
      public boolean verifyOtp(String phoneNumber, String otp) {
        if (otpStore.containsKey(phoneNumber) && otpStore.get(phoneNumber).equals(otp)) {
          ArenaOwner owner = arena.findByPhone(phoneNumber).get();
        //   owner.setVerified(true);
          arena.save(owner);
          otpStore.remove(phoneNumber);
          return true;
        }
        return false;
      }
    
    
}
