package com.adt.registering.application.commands.handlers;

import com.adt.registering.application.commands.CreateEventCommand;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.entities.Notification;
import com.adt.registering.domain.services.EventService;
import com.adt.registering.domain.services.LogService;
import com.adt.registering.domain.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateEventCommandHandler {
    private final EventService eventService;
    private final LogService logService;
    private final NotificationService notificationService;

    @Value("${messaging.notifications.channel-name}")
    private String notificationChannel;

    public Mono<Event> handle(CreateEventCommand command) {
        Event event = new Event();

        event.setType(command.type());
        event.setDescription(command.description());

        logService.log(Log.info("** Starting event registration **"));

        return eventService.create(event)
                .doOnSuccess(saved -> successfulEvent(command, saved))
                .doOnError(error -> failedEvent(command, error))
                .doFinally(signalType ->
                        logService.log(Log.info("** Event registration completed with signal " + signalType + " **")));
    }

    private void successfulEvent(CreateEventCommand command, Event event) {
        Log log = Log.info(
                "Event created: " + event.getId()
                        + " - with command: " + command
        );

        Notification notification = Notification.success(
                notificationChannel,
                "Event created",
                "Event created successfully: " + event.getId()
        );

        logService.log(log);
        notificationService.notify(notification);
    }

    private void failedEvent(CreateEventCommand command, Throwable error) {
        Log log = Log.error(
                "Error creating event: " + error.getMessage()
                        + " - with command: " + command
        );

        Notification notification = Notification.error(
                notificationChannel,
                "Error creating event",
                "Error creating event: " + error.getMessage()
        );

        logService.log(log);
        notificationService.notify(notification);
    }
}
