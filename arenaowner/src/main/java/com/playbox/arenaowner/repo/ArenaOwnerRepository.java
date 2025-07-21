package com.playbox.arenaowner.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.arenaowner.entity.ArenaOwner;

public interface ArenaOwnerRepository extends JpaRepository<ArenaOwner, Long> {
    Optional<ArenaOwner> findByEmail(String email);
    Optional<ArenaOwner> findByPhone(String phoneNumber);

}
