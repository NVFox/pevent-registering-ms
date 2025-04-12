package com.adt.registering.application.queries.handlers;

import com.adt.registering.application.queries.GetEventsByQuery;
import com.adt.registering.domain.entities.Event;
import com.adt.registering.domain.entities.Log;
import com.adt.registering.domain.services.EventService;
import com.adt.registering.domain.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class GetEventsByQueryHandler {
    private final EventService eventService;
    private final LogService logService;

    public Mono<Page<Event>> handle(GetEventsByQuery query) {
        return logService.create(Log.info("** Starting event query with filters: " + query.filter() + " **"))
                .then(eventService.findBy(query.filter(), query.pageable()))
                .publishOn(Schedulers.boundedElastic())
                .doOnError(error ->
                        log(Log.error("Error querying events: " + error.getMessage())))
                .doOnSuccess(saved ->
                        log(Log.info("Events retrieved successfully (" + saved.getTotalElements() + ")")))
                .doFinally(signalType ->
                        log(Log.info("** Event query completed with signal " + signalType + " **")));
    }

    private void log(Log log) {
        logService.create(log)
                .retry(3)
                .subscribe();
    }
}
