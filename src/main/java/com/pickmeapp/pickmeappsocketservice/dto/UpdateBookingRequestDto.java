package com.pickmeapp.pickmeappsocketservice.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingRequestDto {
    private String status;
    private Long bookingId;
    private Optional<Long> driverId;
}
