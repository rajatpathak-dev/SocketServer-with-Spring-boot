package com.pickmeapp.pickmeappsocketservice.controller;

import com.pickmeapp.pickmeappsocketservice.dto.*;
import com.pickmeapp.pickmeappsocketservice.service.KafkaMessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/socket")
public class DriveRequestController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final KafkaMessagePublisher kafkaMessagePublisher;


    public DriveRequestController(SimpMessagingTemplate simpMessagingTemplate, KafkaMessagePublisher kafkaMessagePublisher){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }


    @PostMapping("/newride")
    public ResponseEntity<?> raisedRideRequest(@RequestBody RideRequestDto rideRequestDto){
        sendDriversNewRequest(rideRequestDto);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    public void sendDriversNewRequest(RideRequestDto rideRequestDto){
        System.out.println("executed sendDriverNewReques function");
        int n = rideRequestDto.getDriverId().size();
        for(int i=0 ; i<n ; i++) {
            simpMessagingTemplate.convertAndSend("/topic/driver/" + rideRequestDto.getDriverId().get(i), rideRequestDto);
        }
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto){
        System.out.println("response: "+rideResponseDto.getResponse());
        UpdateBookingRequestDto updateBookingRequestDto = UpdateBookingRequestDto.builder()
                                                          .driverId(Optional.of(Long.parseLong(userId)))
                                                          .status("SCHEDULED")
                                                          .bookingId(rideResponseDto.getBookingId())
                                                          .build();
//        ResponseEntity<UpdateBookingResponseDto> result = restTemplate.postForEntity("http://localhost:8000/api/v1/booking/"+rideResponseDto.getBookingId(),updateBookingRequestDto, UpdateBookingResponseDto.class);
        kafkaMessagePublisher.sendUpdatedBookingRequest(updateBookingRequestDto);

    }

    @MessageMapping("/driverlocation")
    public synchronized void driverLocationHandler(SaveDriverLocationRequestDto saveDriverLocationRequestDto){
        System.out.println("inside driver location saver");
        kafkaMessagePublisher.sendSaveDriverLocation(saveDriverLocationRequestDto);
    }
    
    @GetMapping("/hello")
    public String getHello(){
        return "Hello to test bind mount in socket service";
    }


}
