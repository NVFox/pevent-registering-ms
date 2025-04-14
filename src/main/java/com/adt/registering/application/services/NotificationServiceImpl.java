package com.adt.registering.application.services;

import com.adt.registering.application.providers.MessagingPublisher;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MessagingPublisher messagingPublisher;

    @Value("${messaging.notifications.channel-name}")
    private String notificationChannel;

    @Override
    public void notify(Event event) {
        messagingPublisher.publish(notificationChannel, event);
    }
}
