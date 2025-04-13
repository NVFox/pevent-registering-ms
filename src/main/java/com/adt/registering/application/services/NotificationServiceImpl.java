package com.adt.registering.application.services;

import com.adt.registering.application.providers.MessagingProvider;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MessagingProvider messagingProvider;

    @Value("${messaging.notifications.channel-name}")
    private String notificationChannel;

    @Override
    public void notify(Event event) {
        messagingProvider.publish(notificationChannel, event);
    }
}
