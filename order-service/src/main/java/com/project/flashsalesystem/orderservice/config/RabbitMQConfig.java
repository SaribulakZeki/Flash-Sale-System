package com.project.flashsalesystem.orderservice.config; // Paket ismini kontrol et (yoksa .config kısmını sil)

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "notification-exchange";
    public static final String ROUTING_KEY = "notification.email";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
}