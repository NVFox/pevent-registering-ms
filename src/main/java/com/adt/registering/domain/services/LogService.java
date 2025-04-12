package com.adt.registering.domain.services;

import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.repositories.filters.LogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface LogService {
    Mono<Log> create(Log log);
    Mono<Page<Log>> findBy(LogFilter filter, Pageable pageable);
}
