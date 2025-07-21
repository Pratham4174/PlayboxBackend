package com.playbox.user.dto;

import lombok.Data;

@Data
public class OtpVerifyDto {
  private String phoneNumber;
  private String otp;
}
