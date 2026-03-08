package com.project.flashsalesystem.orderservice.service;

import com.project.flashsalesystem.orderservice.config.RabbitMQConfig;
import com.project.flashsalesystem.orderservice.entity.Order;
import com.project.flashsalesystem.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @KafkaListener(topics = "order-create", groupId = "flash-sale-group")
    public void consume(String message) {
        log.info("Received order-create event from Kafka for productId: {}", message);
        Long productId = Long.parseLong(message);

        Order order = Order.builder()
                .productId(productId)
                .userId(1L)
                .orderDate(LocalDateTime.now())
                .status("SUCCESS")
                .build();

        orderRepository.save(order);
        log.info("Order saved successfully to database. Order ID: {}", order.getId());

        String notificationMessage = "Order Successful! Order ID: " + order.getId();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                notificationMessage
        );

        log.info("Notification event routed to RabbitMQ for Order ID: {}", order.getId());
    }
}