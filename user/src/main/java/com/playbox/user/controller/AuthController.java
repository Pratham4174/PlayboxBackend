package com.playbox.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playbox.user.dto.OTPRequestDto;
import com.playbox.user.dto.OtpVerifyDto;
import com.playbox.user.dto.UserResponseDto;
import com.playbox.user.dto.UserSetupRequestDto;
import com.playbox.user.entity.User;
import com.playbox.user.repo.UserRepository;
import com.playbox.user.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/send-otp")
  public ResponseEntity<String> sendOtp(@RequestBody OTPRequestDto request) {
    authService.sendOtp(request.getPhoneNumber());
    return ResponseEntity.ok("OTP sent");
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyDto request) {
    boolean success = authService.verifyOtp(request.getPhoneNumber(), request.getOtp());
    return success ? ResponseEntity.ok("OTP verified") : ResponseEntity.status(401).body("Invalid OTP");
  }

  @GetMapping("/user")
  public ResponseEntity<UserResponseDto> getUser(@RequestParam String phone) {
      User user = userRepository.findByPhoneNumber(phone)
          .orElseThrow(() -> new RuntimeException("User not found"));
  
      UserResponseDto dto = new UserResponseDto(
        user.getId(),
          user.getPhoneNumber(),
          user.getName(),
          user.getRole().name(),
          user.isVerified()
      );
  
      return ResponseEntity.ok(dto);
  }
  
   @PostMapping("/setup")
  public ResponseEntity<String> setupProfile(@RequestBody UserSetupRequestDto request) {
    authService.setupUserProfile(request);
    return ResponseEntity.ok("Profile updated successfully");
}


}
