package com.adt.registering.application.services;

import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.repositories.LogRepository;
import com.adt.registering.domain.repositories.filters.LogFilter;
import com.adt.registering.domain.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Override
    public Mono<Log> create(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Mono<Page<Log>> findBy(LogFilter filter, Pageable pageable) {
        return logRepository.findBy(filter, pageable);
    }
}
