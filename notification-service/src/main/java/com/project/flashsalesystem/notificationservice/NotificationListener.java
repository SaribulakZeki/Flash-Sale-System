package com.project.flashsalesystem.notificationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notification-queue", durable = "false"),
            exchange = @Exchange(value = "notification-exchange", type = "topic"),
            key = "notification.email"
    ))
    public void handleNotification(String message) {
        log.info("Email notification processed for message: {}", message);
    }
}