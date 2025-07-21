package com.playbox.arenaowner.dto;

import lombok.Data;

@Data
public class OTPVerifyDto {
  private String phoneNumber;
  private String otp;
}
