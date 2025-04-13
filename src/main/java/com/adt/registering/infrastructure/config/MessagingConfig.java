package com.adt.registering.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessagingConfig {
    @Bean
    public Queue logQueue(@Value("${messaging.notifications-logs.queue-name}") String logQueue) {
        return new Queue(logQueue, true);
    }

    @Bean
    public Queue notificationQueue(@Value("${messaging.notifications.queue-name}") String notificationQueue) {
        return new Queue(notificationQueue, true);
    }

    @Bean
    public Exchange notificationsExchange(@Value("${messaging.notifications.topic-name}") String notificationTopic) {
        return new TopicExchange(notificationTopic);
    }

    @Bean
    public Binding logBinding(@Qualifier("logQueue") Queue logQueue,
                              Exchange notificationsExchange,
                              @Value("${messaging.notifications-logs.channel-name}") String channelName) {
        return BindingBuilder.bind(logQueue)
                .to(notificationsExchange)
                .with(channelName)
                .noargs();
    }

    @Bean
    public Binding notificationBinding(@Qualifier("notificationQueue") Queue notificationQueue,
                                       Exchange notificationsExchange,
                                       @Value("${messaging.notifications.channel-name}") String channelName) {
        return BindingBuilder.bind(notificationQueue)
                .to(notificationsExchange)
                .with(channelName)
                .noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
