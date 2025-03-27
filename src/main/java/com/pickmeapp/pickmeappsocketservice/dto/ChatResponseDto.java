package com.pickmeapp.pickmeappsocketservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
    private String message;
    private String name;
    private String timeStamp;
}
