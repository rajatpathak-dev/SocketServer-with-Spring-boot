package com.pickmeapp.pickmeappsocketservice.configurations;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${KAFKA_SERVER}")
    private String kafkaServerUrl;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaServerUrl);
        return new KafkaAdmin(configs);
    }


    @Bean
    public NewTopic  createTopicUpdateBooking(){
        return new NewTopic("PickMeApp-SocketPublisher",3,(short) 1);
    }

    @Bean
    public NewTopic  createTopicSaveDriverLocation(){
        return new NewTopic("PickMeApp-UpdateDriverLoc",3,(short) 1);
    }

    @Bean
    public Map<String,Object> producerConfig(){
        System.out.println("Kafka url is "+kafkaServerUrl);
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaServerUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return  new KafkaTemplate<>(producerFactory());
    }

}
