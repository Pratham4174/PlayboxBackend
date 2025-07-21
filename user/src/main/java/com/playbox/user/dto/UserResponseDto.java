package com.playbox.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String phoneNumber;
  private String name;
  private String role;
  private boolean isVerified;
}

