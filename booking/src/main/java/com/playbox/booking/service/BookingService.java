package com.playbox.booking.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playbox.booking.dto.BookedSlotDTO;
import com.playbox.booking.dto.BookingRequest;
import com.playbox.booking.dto.VenueDTO;
import com.playbox.booking.entity.Booking;
import com.playbox.booking.exception.SlotAlreadyBookedException;
import com.playbox.booking.repo.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    @Autowired
private VenueIntegrationService venueIntegrationService;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(BookingRequest request) {
        boolean slotAvailable = isSlotAvailable(request.getVenueId(),request.getSport(), request.getStartTime(), request.getEndTime());
       if (!slotAvailable) {
        throw new SlotAlreadyBookedException("Slot already booked");
    }
    
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setUserName(request.getUserName());
        booking.setVenueId(request.getVenueId());
        booking.setSport(request.getSport());
        booking.setOwnerId(request.getOwnerId());
        booking.setVenueLocation(request.getVenueLocation());
        booking.setVenueName(request.getVenueName());
        booking.setSlotType(request.getSlotType());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setCreatedAt(LocalDateTime.now());
    
        booking.setActive(booking.getEndTime().isAfter(LocalDateTime.now()));
    
        return bookingRepository.save(booking);
    }
    

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();
    
        for (Booking booking : bookings) {
            boolean active = booking.getEndTime().isAfter(now);
            booking.setActive(active);
        }
    
        // Save updated active status to DB
        bookingRepository.saveAll(bookings);
    
        return bookings;
    }
    

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public void updateBookingStatus() {
        List<Booking> allBookings = bookingRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : allBookings) {
            boolean active = booking.getEndTime().isAfter(now);
            booking.setActive(active);
        }

        bookingRepository.saveAll(allBookings);
    }

    public List<Booking> getBookingsByVenue(Long venueId) {
        return bookingRepository.findByVenueId(venueId);
    }

    // BookingService.java
public boolean isSlotAvailable(Long venueId, String sport,LocalDateTime startTime, LocalDateTime endTime) {
    List<Booking> existing = bookingRepository.findBookingsByVenueIdAndTimeRange(venueId,sport, startTime, endTime);
    return existing.isEmpty();
}

public List<Booking> getBookingsByOwner(Long ownerId) {
    return bookingRepository.findByOwnerId(ownerId);
}

// public List<Booking> getBookingsByOwnerAndSport(Long ownerId, String sport) {
//     return bookingRepository.findByOwnerIdAndSport(ownerId, sport);
// }



public void fetchBookingsForOwner(Long ownerId) {
    List<VenueDTO> venues = venueIntegrationService.getVenuesByOwnerId(ownerId);
    List<Long> venueIds = venues.stream().map(VenueDTO::getId).collect(Collectors.toList());

    List<Booking> bookings = bookingRepository.findByVenueIdIn(venueIds);
    // proceed with response
}
public List<BookedSlotDTO> getBookedSlotsByVenueAndDate(Long venueId, String dateStr) {
    LocalDate bookingDate = LocalDate.parse(dateStr);
    List<Booking> bookings = bookingRepository.findByVenueIdAndDate(venueId, bookingDate);

    return bookings.stream()
        .map(booking -> new BookedSlotDTO(
                booking.getSlotType(),
                booking.getStartTime(),
                booking.getEndTime()
        ))
        .toList();
}
public List<BookedSlotDTO> getBookedSlotsByVenueDateAndSport(Long venueId, String date, String sport) {
    LocalDate bookingDate = LocalDate.parse(date);
    LocalDateTime startOfDay = bookingDate.atStartOfDay();
    LocalDateTime endOfDay = bookingDate.atTime(LocalTime.MAX);

    List<Booking> bookings = bookingRepository.findByVenueIdAndSportAndStartTimeBetween(
        venueId, sport, startOfDay, endOfDay
    );

    return bookings.stream()
        .map(b -> new BookedSlotDTO(b.getSlotType(),b.getStartTime(), b.getEndTime()))
        .collect(Collectors.toList());
}


}
