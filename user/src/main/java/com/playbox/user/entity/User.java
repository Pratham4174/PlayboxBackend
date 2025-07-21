package com.playbox.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;
  
  @Column(name = "name")
  private String name;

  @Enumerated(EnumType.STRING)
  private Role role = Role.PLAYER;

  private boolean isVerified = false;

  private LocalDateTime createdAt = LocalDateTime.now();

  public enum Role {
    PLAYER,
    ARENA_OWNER
  }
}
