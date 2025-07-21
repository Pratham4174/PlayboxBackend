package com.playbox.user.dto;
import lombok.Data;

@Data
public class UserSetupRequestDto {
    private String phoneNumber;
    private String name;
    private String location;
}

