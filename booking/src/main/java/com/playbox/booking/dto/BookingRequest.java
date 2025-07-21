package com.playbox.booking.dto;



import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private String userName;
    private Long venueId;
    private String venueName;
    private String sport;
    private String venueLocation;
    private String slotType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long ownerId; // Owner of the venue
}
