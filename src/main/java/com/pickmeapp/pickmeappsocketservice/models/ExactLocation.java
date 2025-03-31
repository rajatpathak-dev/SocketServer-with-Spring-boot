package com.pickmeapp.pickmeappsocketservice.models;

import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExactLocation {
    private  double latitude;
    private double longitude;
}
