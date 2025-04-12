package com.adt.registering.domain.services;

import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.repositories.filters.LogFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LogService {
    Mono<Log> create(Log log);
    Flux<Log> findBy(LogFilter filter);
}
