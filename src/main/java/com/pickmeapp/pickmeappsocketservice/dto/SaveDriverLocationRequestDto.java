package com.pickmeapp.pickmeappsocketservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveDriverLocationRequestDto {
    private String driverId;
    private Double latitude;
    private Double longitude;
}
