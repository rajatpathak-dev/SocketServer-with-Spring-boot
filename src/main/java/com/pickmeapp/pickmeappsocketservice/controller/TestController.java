package com.pickmeapp.pickmeappsocketservice.controller;

import com.pickmeapp.pickmeappsocketservice.dto.ChatRequestDto;
import com.pickmeapp.pickmeappsocketservice.dto.ChatResponseDto;
import com.pickmeapp.pickmeappsocketservice.dto.TestRequestDto;
import com.pickmeapp.pickmeappsocketservice.dto.TestResponseDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponseDto ping(TestRequestDto testRequestDto){
        System.out.println("message received from client"+testRequestDto.getData());
        return TestResponseDto.builder().data("Recieved").build();
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ChatResponseDto chatMessage(ChatRequestDto chatRequestDto){
        return  ChatResponseDto.builder()
                .name(chatRequestDto.getName())
                .message(chatRequestDto.getMessage())
                .timeStamp(""+System.currentTimeMillis())
                .build();
    }
}
