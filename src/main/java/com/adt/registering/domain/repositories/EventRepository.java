package com.adt.registering.domain.repositories;

import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.repositories.filters.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Mono<Event> save(Event event);
    Mono<Page<Event>> findBy(EventFilter filter, Pageable pageable);
}
