package com.adt.registering.infrastructure.http.controllers;

import com.adt.registering.application.commands.CreateEventCommand;
import com.adt.registering.application.commands.handlers.CreateEventCommandHandler;
import com.adt.registering.application.dtos.EventQueryParamsDTO;
import com.adt.registering.application.queries.GetEventsByQuery;
import com.adt.registering.application.queries.handlers.GetEventsByQueryHandler;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.repositories.filters.EventFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final CreateEventCommandHandler createEventCommandHandler;
    private final GetEventsByQueryHandler getEventsByQueryHandler;

    @PostMapping
    public Mono<Event> createEvent(@RequestBody CreateEventCommand command) {
        return createEventCommandHandler.handle(command);
    }

    @GetMapping
    public Mono<Page<Event>> getEventsBy(EventQueryParamsDTO queryParams) {
        EventFilter filter = new EventFilter(
                queryParams.getType(),
                queryParams.getFrom(),
                queryParams.getTo()
        );

        PageRequest pageRequest = PageRequest.of(
                queryParams.getPage(),
                queryParams.getSize()
        );

        return getEventsByQueryHandler.handle(new GetEventsByQuery(filter, pageRequest));
    }
}
