package com.playbox.arenaowner.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.playbox.arenaowner.dto.VenueRequest;
import com.playbox.arenaowner.entity.SportPrice;
import com.playbox.arenaowner.entity.Venue;
import com.playbox.arenaowner.entity.VenueImage;
import com.playbox.arenaowner.repo.VenueRepository;
import com.playbox.arenaowner.service.VenueService;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin
public class VenueController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    @Autowired
    private VenueService venueService;
    @Autowired
    private VenueRepository venueRepository;

    // Endpoint for adding venue (data in JSON)
    @PostMapping("/add")
    public Venue addVenue(@RequestBody VenueRequest request) {
        return venueService.addVenue(request);
    }

    // Endpoint for uploading image
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestParam("file")  MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create the uploads directory if it doesn't exist
            }
    
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
            file.transferTo(dest);
    
            // Return the public URL (adjust as needed for frontend)
            return "http://localhost:8092/uploads/" + fileName;
    
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
    

        @GetMapping("/owner/{ownerId}")
    public List<Venue> getVenuesByOwner(@PathVariable Long ownerId) {
        return venueRepository.findByOwnerId(ownerId);
    }

    @PutMapping("/update/{id}")
public ResponseEntity<?> updateVenue(@PathVariable Long id, @RequestBody Venue updatedVenue) {
    Optional<Venue> optionalVenue = venueRepository.findById(id);
    if (optionalVenue.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venue not found");
    }

    Venue venue = optionalVenue.get();

    // Update basic fields
    venue.setName(updatedVenue.getName());
    venue.setLocation(updatedVenue.getLocation());
    venue.setCity(updatedVenue.getCity());
    venue.setState(updatedVenue.getState());
    venue.setPincode(updatedVenue.getPincode());
    venue.setCoordinates(updatedVenue.getCoordinates());
    venue.setContactNumber(updatedVenue.getContactNumber());
    venue.setDescription(updatedVenue.getDescription());
    venue.setOperationTime(updatedVenue.getOperationTime());
    venue.setAmenities(updatedVenue.getAmenities());

    // Update sportPrices
    venue.getSportPrices().clear();
    List<SportPrice> updatedSportPrices = updatedVenue.getSportPrices();
    if (updatedSportPrices != null) {
        for (SportPrice sp : updatedSportPrices) {
            sp.setVenue(venue); // maintain relationship
            venue.getSportPrices().add(sp);
        }
    }

    // Update images
    venue.getImages().clear();
    List<VenueImage> updatedImages = updatedVenue.getImages();
    if (updatedImages != null) {
        for (VenueImage image : updatedImages) {
            image.setVenue(venue); // maintain relationship
            venue.getImages().add(image);
        }
    }

    venueRepository.save(venue);

    return ResponseEntity.ok("Venue updated successfully");
}
@GetMapping("/{id}")
public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
    Optional<Venue> venue = venueRepository.findById(id);
    return venue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
}

@GetMapping("/all")
public ResponseEntity<List<Venue>> getAllVenues() {
    List<Venue> venues = venueRepository.findAll();
    return ResponseEntity.ok(venues);
}

}

