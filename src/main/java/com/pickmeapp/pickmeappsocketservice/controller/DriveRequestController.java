package com.pickmeapp.pickmeappsocketservice.controller;

import com.pickmeapp.pickmeappsocketservice.dto.RideRequestDto;
import com.pickmeapp.pickmeappsocketservice.dto.RideResponseDto;
import com.pickmeapp.pickmeappsocketservice.dto.UpdateBookingRequestDto;
import com.pickmeapp.pickmeappsocketservice.dto.UpdateBookingResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/socket")
public class DriveRequestController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RestTemplate restTemplate;


    public DriveRequestController(SimpMessagingTemplate simpMessagingTemplate)                                 {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = new RestTemplate();
    }


    @PostMapping("/newride")
    public ResponseEntity<?> raisedRideRequest(@RequestBody RideRequestDto rideRequestDto){
        sendDriversNewRequest(rideRequestDto);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    public void sendDriversNewRequest(RideRequestDto rideRequestDto){
        System.out.println("executed sendDriverNewReques function");
        simpMessagingTemplate.convertAndSend("/topic/rideRequest",rideRequestDto);
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto){
        System.out.println("response: "+rideResponseDto.getResponse());
        UpdateBookingRequestDto updateBookingRequestDto = UpdateBookingRequestDto.builder()
                                                          .driverId(Optional.of(Long.parseLong(userId)))
                                                          .status("SCHEDULED")
                                                          .build();

        ResponseEntity<UpdateBookingResponseDto> result = restTemplate.postForEntity("http://localhost:8000/api/v1/booking/"+rideResponseDto.getBookingId(),updateBookingRequestDto, UpdateBookingResponseDto.class);
        System.out.println("Status code is "+result.getStatusCode());
    }


}
