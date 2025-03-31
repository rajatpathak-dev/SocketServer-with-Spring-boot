package com.pickmeapp.pickmeappsocketservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideResponseDto {
    private Boolean response;
    private Long bookingId;
}
