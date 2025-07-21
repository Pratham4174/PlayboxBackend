package com.playbox.booking.repo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.playbox.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByVenueId(Long venueId);
    List<Booking> findByIsActive(boolean isActive);
    List<Booking> findByOwnerId(@Param("ownerId") Long ownerId);
    List<Booking> findByOwnerIdAndSport(Long ownerId, String sport);
    List<Booking> findByVenueIdIn(List<Long> venueIds);


    @Query("SELECT b FROM Booking b " +
    "WHERE b.venueId = :venueId " +
    "AND b.sport = :sport " +
    "AND b.startTime < :endTime " +
    "AND b.endTime > :startTime")
List<Booking> findBookingsByVenueIdAndTimeRange(@Param("venueId") Long venueId,
                                              @Param("sport") String sport,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
                                                                                        

// @Query("SELECT b FROM Booking b WHERE b.venueId IN (SELECT v.id FROM Venue v WHERE v.owner.id = :ownerId AND v.sport = :sport)")
// List<Booking> findByOwnerAndSport(@Param("ownerId") Long ownerId, @Param("sport") String sport);

// @Query("SELECT b FROM Booking b WHERE b.venueId IN (SELECT v.id FROM Venue v WHERE v.ownerId = :ownerId AND v.sport = :sport)")
// List<Booking> findByOwnerIdAndSport(@Param("ownerId") Long ownerId, @Param("sport") String sport);
@Query("SELECT b FROM Booking b WHERE b.venueId = :venueId AND DATE(b.startTime) = :bookingDate")
List<Booking> findByVenueIdAndDate(@Param("venueId") Long venueId, @Param("bookingDate") LocalDate bookingDate);

List<Booking> findByVenueIdAndSportAndStartTimeBetween(
    Long venueId,
    String sport,
    LocalDateTime start,
    LocalDateTime end
);

List<Booking> findByCourtIdAndStartTimeBetween(Long courtId, LocalDateTime start, LocalDateTime end);


@Query("SELECT b FROM Booking b WHERE " +
"b.venueId = :venueId AND " +
"DATE(b.startTime) = :date AND " +
"b.courtName = :courtName")
List<Booking> findByVenueDateAndCourt(
@Param("venueId") Long venueId,
@Param("date") LocalDate date,
@Param("courtName") String courtName
);
}
