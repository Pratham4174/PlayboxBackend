package com.playbox.booking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.booking.entity.Court;

public interface CourtRepository  extends JpaRepository<Court, Long>{

    List<Court> findByVenueIdAndSport(Long venueId, String sport);
}
