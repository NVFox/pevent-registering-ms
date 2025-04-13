package com.adt.registering.infrastructure.messaging;

import com.adt.registering.application.providers.MessagingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMessagingProvider implements MessagingProvider {
    private final RabbitTemplate rabbitTemplate;

    @Value("${messaging.notifications.topic-name}")
    private String topic;

    @Override
    public void publish(String queue, Object message) {
        rabbitTemplate.convertAndSend(topic, queue, message);
    }
}
