package com.example.backend.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BackendEventsConsumer {

    @KafkaListener(topics = "backend-events")
    public void onMessage(ConsumerRecord<String, String> record) {
        String logString = String.format("Kafka received: key=%s value=%s", record.key(), record.value());
        System.out.println(logString);
    }
}
