package com.playbox.booking.dto;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookedSlotDTO {
    private String slotType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
