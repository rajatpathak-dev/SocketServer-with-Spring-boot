package com.pickmeapp.pickmeappsocketservice.service;

import com.pickmeapp.pickmeappsocketservice.dto.UpdateBookingRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    private KafkaTemplate<String,Object> template;

    public KafkaMessagePublisher(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendEventsToTopic(UpdateBookingRequestDto updateBookingResponseDto) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("PickMeApp-SocketPublisher", updateBookingResponseDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Send Message=[" + updateBookingResponseDto.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            updateBookingResponseDto.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e){
            System.out.println("Error :"+e.getMessage());
        }
    }


}
