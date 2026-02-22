package com.project.flashsalesystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderCreateTopic() {
        return TopicBuilder.name("order-create")
                .partitions(1)
                .replicas(1)
                .build();
    }
}