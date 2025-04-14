package com.adt.registering.application.services;

import com.adt.registering.application.providers.MessagePublisher;
import com.adt.registering.domain.entities.Notification;
import com.adt.registering.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MessagePublisher messagePublisher;

    @Value("${messaging.notifications.channel-name}")
    private String notificationChannel;

    @Override
    public void notify(Notification notification) {
        messagePublisher.publish(notificationChannel, notification);
    }
}
