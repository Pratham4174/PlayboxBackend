package com.playbox.user.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playbox.user.dto.UserResponseDto;
import com.playbox.user.dto.UserSetupRequestDto;
import com.playbox.user.entity.User;
import com.playbox.user.repo.UserRepository;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  private Map<String, String> otpStore = new ConcurrentHashMap<>();

  public void sendOtp(String phoneNumber) {
    // if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
    //     throw new IllegalArgumentException("Phone number is required");
    // }
   System.out.println("Sending OTP to: " + phoneNumber);
    String otp = "1234"; // Fake OTP for now
    otpStore.put(phoneNumber, otp);

    userRepository.findByPhoneNumber(phoneNumber).orElseGet(() -> {
      User user = new User();
      user.setPhoneNumber(phoneNumber);
      return userRepository.save(user);
    });

    System.out.println("Mock OTP sent: " + otp);
  }

  public boolean verifyOtp(String phoneNumber, String otp) {
    if (otpStore.containsKey(phoneNumber) && otpStore.get(phoneNumber).equals(otp)) {
      User user = userRepository.findByPhoneNumber(phoneNumber).get();
      user.setVerified(true);
      userRepository.save(user);
      otpStore.remove(phoneNumber);
      return true;
    }
    return false;
  }

  public UserResponseDto getUser(String phoneNumber) {
    User user = userRepository.findByPhoneNumber(phoneNumber)
      .orElseThrow(() -> new RuntimeException("User not found"));

    return new UserResponseDto(
      user.getId(),
      user.getPhoneNumber(),
      user.getName(),
      user.getRole().name(),
      user.isVerified()
    );
  }

  public void setupUserProfile(UserSetupRequestDto request) {
    User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
        .orElseThrow(() -> new RuntimeException("User not found"));
    if(user.getName()== null) {
      user.setName(request.getName());
    }  
    // user.setLocation(request.getLocation());
    userRepository.save(user);
}

}
