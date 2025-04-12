package com.adt.registering.application.services;

import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.repositories.EventRepository;
import com.adt.registering.domain.services.EventService;
import com.adt.registering.domain.repositories.filters.EventFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Mono<Event> create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Mono<Page<Event>> findBy(EventFilter filter, Pageable pageable) {
        return eventRepository.findBy(filter, pageable);
    }
}
