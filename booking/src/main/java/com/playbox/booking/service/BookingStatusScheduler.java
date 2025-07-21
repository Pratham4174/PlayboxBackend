package com.playbox.booking.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingStatusScheduler {

    private final BookingService bookingService;

    public BookingStatusScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedRate = 3600000) // Every hour (in milliseconds)
    public void autoUpdateBookingStatus() {
        bookingService.updateBookingStatus();
        System.out.println("Booking status auto-updated by scheduler.");
    }
}
