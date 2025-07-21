package com.playbox.arenaowner.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playbox.arenaowner.dto.VenueRequest;
import com.playbox.arenaowner.entity.ArenaOwner;
import com.playbox.arenaowner.entity.SportPrice;
import com.playbox.arenaowner.entity.Venue;
import com.playbox.arenaowner.entity.VenueImage;
import com.playbox.arenaowner.repo.ArenaOwnerRepository;
import com.playbox.arenaowner.repo.VenueRepository;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ArenaOwnerRepository arenaOwnerRepository;

    public Venue addVenue(VenueRequest request) {
        Venue venue = new Venue();
        venue.setName(request.getVenueName());
        venue.setLocation(request.getLocation());
        venue.setCity(request.getCity());
        venue.setState(request.getState());
        venue.setPincode(request.getPincode());
        venue.setCoordinates(request.getCoordinates());
        venue.setContactNumber(request.getContactNumber());
        venue.setDescription(request.getDescription());
        venue.setOperationTime(request.getOperationTime());
        venue.setAmenities(request.getAmenities());
        ArenaOwner owner = arenaOwnerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + request.getOwnerId()));
        venue.setOwner(owner); // Assuming VenueRequest has an owner field

        // Convert SportPrices
        List<SportPrice> sports = request.getSportPrices().stream()
        .map(dto -> {
            SportPrice price = new SportPrice();
            price.setSport(dto.getSport());
            price.setPricePerHour(dto.getPricePerHour());
            price.setVenue(venue); // back reference
            return price;
        })
        .collect(Collectors.toList());
    venue.setSportPrices(sports);

        // Convert image URLs to VenueImage entities
        List<VenueImage> images = request.getImages().stream()
            .map(imageRequest -> {
                VenueImage image = new VenueImage();
                image.setImageUrl(imageRequest.getUrl());
                image.setVenue(venue); // set back reference
                return image;
            })
            .collect(Collectors.toList());
        venue.setImages(images);

        return venueRepository.save(venue);
    }

}
