package com.adt.registering.domain.repositories;

import com.adt.registering.domain.entities.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {
    Flux<Event> findByTypeAndCreatedAtBetween(String type, LocalDateTime start, LocalDateTime end);
}
