package com.playbox.booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User Info
    private Long userId;             // Registered user ID (nullable if guest)
    private String userName;         // For guest users

    // Venue Info
    private Long venueId;
    private String venueName;
    private String sport;
    private String venueLocation;
    private Long ownerId; 
    // Booking Info
    private String slotType;         // Morning, Evening, Night
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // System Info
    private LocalDateTime createdAt = LocalDateTime.now();

    // Booking status
    private boolean isActive = true; // True if upcoming or ongoing, false if in the past
    private Long courtId;
private String courtName;

}
