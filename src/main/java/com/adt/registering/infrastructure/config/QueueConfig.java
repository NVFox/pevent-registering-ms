package com.adt.registering.infrastructure.config;

import com.adt.registering.application.constants.QueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class QueueConfig {
    @Bean
    public Queue eventQueue() {
        return new Queue(QueueConstants.EVENTS_QUEUE_NAME, false);
    }

    @Bean
    public Exchange notificationsExchange() {
        return new TopicExchange(QueueConstants.NOTIFICATIONS_TOPIC_NAME);
    }

    @Bean
    public Binding eventBinding(Queue eventQueue, Exchange notificationsExchange) {
        return BindingBuilder.bind(eventQueue)
                .to(notificationsExchange)
                .with(QueueConstants.EVENTS_CHANNEL_NAME)
                .noargs();
    }
}
