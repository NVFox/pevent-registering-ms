package com.adt.registering.application.services;

import com.adt.registering.application.constants.QueueConstants;
import com.adt.registering.application.providers.MessagingProvider;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MessagingProvider messagingProvider;

    @Override
    public void notify(Event event) {
        messagingProvider.publish(
                QueueConstants.NOTIFICATIONS_TOPIC_NAME,
                QueueConstants.EVENTS_CHANNEL_NAME,
                event
        );
    }
}
