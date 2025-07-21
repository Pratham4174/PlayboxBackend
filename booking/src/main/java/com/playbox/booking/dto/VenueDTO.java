package com.playbox.booking.dto;

import lombok.Data;

@Data
public class VenueDTO {
    private Long id;
    private String venueName;
    private String city;
    private String state;
    private Long ownerId;
    // Add other fields as needed
}
