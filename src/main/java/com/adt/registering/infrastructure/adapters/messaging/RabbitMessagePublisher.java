package com.adt.registering.infrastructure.adapters.messaging;

import com.adt.registering.application.providers.MessagePublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMessagePublisher implements MessagePublisher {
    private final Sender sender;
    private final ObjectMapper objectMapper;

    @Value("${messaging.notifications.topic-name}")
    private String topic;

    @Override
    public void publish(String channel, Object message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            byte[] bytes = SerializationUtils.serialize(json);

            Flux<OutboundMessage> outbound = Flux.just(new OutboundMessage(
                    topic,
                    channel,
                    bytes
            ));

            sender.sendWithPublishConfirms(outbound)
                    .subscribe(null, e ->
                        log.error("Error publishing message: {}", e.getMessage(), e));
        } catch (Exception e) {
            log.error("Error serializing message: {}", e.getMessage(), e);
        }
    }
}
