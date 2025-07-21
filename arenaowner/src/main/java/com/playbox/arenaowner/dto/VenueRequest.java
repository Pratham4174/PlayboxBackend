package com.playbox.arenaowner.dto;


import java.util.List;

import lombok.Data;

@Data
public class VenueRequest {
    private String venueName;
    private String location;
    private String city;
    private String state;
    private String pincode;
    private String coordinates;
    private String contactNumber;
    private String description;
    private String operationTime;
    private List<String> amenities;
    private List<SportPriceRequest> sportPrices;
    private List<VenueImageRequest> images;
    // Missing field:
private Long ownerId;

}
