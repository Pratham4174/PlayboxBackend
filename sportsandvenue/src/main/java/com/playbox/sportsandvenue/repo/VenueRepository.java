package com.playbox.sportsandvenue.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.sportsandvenue.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}

