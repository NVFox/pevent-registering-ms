package com.adt.registering.infrastructure.config;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class MessagingConfig {
    @Autowired
    private Mono<Connection> connectionMono;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${messaging.notifications.topic-name}")
    private String notificationTopic;

    @Value("${messaging.notifications.queue-name}")
    private String notificationQueue;

    @Value("${messaging.notifications-logs.queue-name}")
    private String logQueue;

    @Value("${messaging.notifications.channel-name}")
    private String notificationChannel;

    @Value("${messaging.notifications-logs.channel-name}")
    private String logChannel;

    @Bean
    public Mono<Connection> connectionMono(RabbitProperties rabbitProperties) {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();

        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.useNio();

        return Mono.fromCallable(() -> connectionFactory
                .newConnection("reactive-notifications-rabbitmq")).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }

    private Exchange notificationsExchange() {
        return new TopicExchange(notificationTopic);
    }

    private Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    private Queue logQueue() {
        return new Queue(logQueue, true);
    }

    private Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(notificationsExchange())
                .with(notificationChannel)
                .noargs();
    }

    private Binding logBinding() {
        return BindingBuilder.bind(logQueue())
                .to(notificationsExchange())
                .with(logChannel)
                .noargs();
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareExchange(notificationsExchange());
        amqpAdmin.declareQueue(notificationQueue());
        amqpAdmin.declareQueue(logQueue());
        amqpAdmin.declareBinding(notificationBinding());
        amqpAdmin.declareBinding(logBinding());
    }

    @PreDestroy
    public void close() throws IOException {
        Objects.requireNonNull(connectionMono.block()).close();
    }
}
