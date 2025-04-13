package com.adt.registering.application.commands.handlers;

import com.adt.registering.application.commands.CreateEventCommand;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.services.EventService;
import com.adt.registering.domain.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateEventCommandHandler {
    private final EventService eventService;
    private final LogService logService;

    public Mono<Event> handle(CreateEventCommand command) {
        Event event = new Event();

        event.setType(command.type());
        event.setDescription(command.description());

        logService.log(Log.info("** Starting event registration **"));

        return eventService.create(event)
                .doOnError(error ->
                        logService.log(Log.error("Error creating event: " + error.getMessage() +
                            " - with command: " + command)))
                .doOnSuccess(saved ->
                        logService.log(Log.info("Event created: " + saved.getId() +
                            " - with command: " + command)))
                .doFinally(signalType ->
                        logService.log(Log.info("** Event registration completed with signal " + signalType + " **")));
    }
}
