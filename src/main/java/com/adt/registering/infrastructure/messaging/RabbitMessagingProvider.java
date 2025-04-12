package com.adt.registering.infrastructure.messaging;

import com.adt.registering.application.providers.MessagingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMessagingProvider implements MessagingProvider {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String topic, String key, Object message) {
        rabbitTemplate.convertAndSend(topic, key, message);
    }
}
