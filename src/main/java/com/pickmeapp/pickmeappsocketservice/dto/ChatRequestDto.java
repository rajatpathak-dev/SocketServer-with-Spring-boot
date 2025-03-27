package com.pickmeapp.pickmeappsocketservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    private String message;
    private String name;
}
