package com.adt.registering.domain.repositories;

import com.adt.registering.domain.entities.Log;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface LogRepository extends ReactiveMongoRepository<Log, String> {
    Flux<Log> findByLevelAndTimestampBetween(Log.Level level, LocalDateTime start, LocalDateTime end);
}
