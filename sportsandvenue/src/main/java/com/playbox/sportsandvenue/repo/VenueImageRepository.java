package com.playbox.sportsandvenue.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.sportsandvenue.entity.VenueImage;

public interface VenueImageRepository extends JpaRepository<VenueImage, Long> {
}

