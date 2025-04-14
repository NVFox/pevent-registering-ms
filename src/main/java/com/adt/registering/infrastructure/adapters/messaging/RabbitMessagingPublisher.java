package com.adt.registering.infrastructure.adapters.messaging;

import com.adt.registering.application.providers.MessagingPublisher;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.services.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Service
@RequiredArgsConstructor
public class RabbitMessagingPublisher implements MessagingPublisher {
    private final Sender sender;
    private final ObjectMapper objectMapper;

    private final LogService logService;

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
                    .doOnError(error ->
                            logService.log(Log.error("Error publishing message: " + error.getMessage())))
                    .subscribe();
        } catch (Exception e) {
            logService.log(Log.error("Error serializing message: " + e.getMessage()));
        }
    }
}
