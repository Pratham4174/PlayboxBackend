package com.playbox.arenaowner.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.arenaowner.entity.ArenaOwner;
import com.playbox.arenaowner.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
     List<Venue> findByOwner(ArenaOwner owner);
     List<Venue> findByOwnerId(Long ownerId);
}
