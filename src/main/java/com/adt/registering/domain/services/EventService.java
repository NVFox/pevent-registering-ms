package com.adt.registering.domain.services;

import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.repositories.filters.EventFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
    Mono<Event> create(Event event);
    Flux<Event> findBy(EventFilter filter);
}
