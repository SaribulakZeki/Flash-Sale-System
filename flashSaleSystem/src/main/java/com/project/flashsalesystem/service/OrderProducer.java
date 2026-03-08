package com.project.flashsalesystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderEvent(Long productId) {
        String message = String.valueOf(productId);
        log.info("Sending order-create event to Kafka for productId: {}", productId);
        kafkaTemplate.send("order-create", message);
    }
}