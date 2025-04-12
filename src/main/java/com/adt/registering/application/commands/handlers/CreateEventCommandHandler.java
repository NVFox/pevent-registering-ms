package com.adt.registering.application.commands.handlers;

import com.adt.registering.application.commands.CreateEventCommand;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateEventCommandHandler {
    private final EventService eventService;

    public Mono<Event> handle(CreateEventCommand command) {
        Event event = new Event();

        event.setType(command.type());
        event.setDescription(command.description());

        return eventService.create(event);
    }
}
