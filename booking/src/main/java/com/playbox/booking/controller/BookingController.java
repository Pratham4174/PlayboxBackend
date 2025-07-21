package com.playbox.booking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playbox.booking.dto.BookedSlotDTO;
import com.playbox.booking.dto.BookingRequest;
import com.playbox.booking.dto.VenueDTO;
import com.playbox.booking.entity.Booking;
import com.playbox.booking.entity.Court;
import com.playbox.booking.repo.CourtRepository;
import com.playbox.booking.service.BookingService;
import com.playbox.booking.service.VenueIntegrationService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    @Autowired
    private VenueIntegrationService venueIntegrationService;
    @Autowired
    private CourtRepository courtRepository;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-status")
    public ResponseEntity<Void> updateBookingStatus() {
        bookingService.updateBookingStatus();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/venue/{venueId}")
public ResponseEntity<List<Booking>> getBookingsByVenue(@PathVariable Long venueId) {
    return ResponseEntity.ok(bookingService.getBookingsByVenue(venueId));
}


@GetMapping("/owner/{ownerId}")
public ResponseEntity<List<Booking>> getBookingsByOwner(@PathVariable Long ownerId) {
    return ResponseEntity.ok(bookingService.getBookingsByOwner(ownerId));
}

// @GetMapping("/owner/{ownerId}/sport/{sport}")
// public ResponseEntity<List<Booking>> getBookingsByOwnerAndSport(
//         @PathVariable Long ownerId, @PathVariable String sport) {
//     return ResponseEntity.ok(bookingService.getBookingsByOwnerAndSport(ownerId, sport));
// }
@GetMapping("/test/venues-by-owner/{ownerId}")
public ResponseEntity<List<Long>> testFetchVenues(@PathVariable Long ownerId) {
    List<VenueDTO> venues = venueIntegrationService.getVenuesByOwnerId(ownerId);
    List<Long> ids = venues.stream().map(VenueDTO::getId).toList();
    return ResponseEntity.ok(ids);
}

@GetMapping("/venue/{venueId}/date/{date}")
public ResponseEntity<List<BookedSlotDTO>> getBookedSlotsByVenueDateAndSport(
        @PathVariable Long venueId,
        @PathVariable String date, // format: yyyy-MM-dd
        @RequestParam String sport
) {
    List<BookedSlotDTO> bookedSlots = bookingService.getBookedSlotsByVenueDateAndSport(venueId, date, sport);
    return ResponseEntity.ok(bookedSlots);
}

@GetMapping("/venuebycourt/{venueId}/date/{date}")
public ResponseEntity<List<BookedSlotDTO>> getBookedSlotsByVenueDateSportAndVenueId(
        @PathVariable Long venueId,
        @PathVariable String date, // format: yyyy-MM-dd
        @RequestParam String sport,
        @RequestParam Long courtId
) {
    List<BookedSlotDTO> bookedSlots = bookingService.getBookedSlotsByVenueDateSportAndCourtId(venueId, date, sport, courtId);
    return ResponseEntity.ok(bookedSlots);
}

@GetMapping("/courts")
public List<Court> getCourts(@RequestParam Long venueId, @RequestParam String sport) {
    return courtRepository.findByVenueIdAndSport(venueId, sport);
}



}
