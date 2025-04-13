package com.adt.registering.infrastructure.http.controllers;

import com.adt.registering.application.dtos.LogQueryParamsDTO;
import com.adt.registering.application.queries.GetLogsByQuery;
import com.adt.registering.application.queries.handlers.GetLogsByQueryHandler;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.repositories.filters.LogFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {
    private final GetLogsByQueryHandler getLogsByQueryHandler;

    @GetMapping
    public Mono<Page<Log>> getLogsBy(LogQueryParamsDTO queryParams) {
        LogFilter filter = new LogFilter(
                queryParams.getTraceId(),
                queryParams.getLevel(),
                queryParams.getMessage(),
                queryParams.getFrom(),
                queryParams.getTo()
        );

        PageRequest pageRequest = PageRequest.of(
                queryParams.getPage(),
                queryParams.getSize()
        );

        return getLogsByQueryHandler.handle(new GetLogsByQuery(filter, pageRequest));
    }
}
