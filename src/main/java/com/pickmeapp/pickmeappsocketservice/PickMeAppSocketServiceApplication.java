package com.pickmeapp.pickmeappsocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.pickme.pickmeappentityservice.models")
public class PickMeAppSocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickMeAppSocketServiceApplication.class, args);
    }

}
