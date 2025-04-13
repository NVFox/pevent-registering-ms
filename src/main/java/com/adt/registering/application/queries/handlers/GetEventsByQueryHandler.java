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
        logService.log(Log.info("** Starting event query with filters: " + query.filter() + " **"));

        return eventService.findBy(query.filter(), query.pageable())
                .publishOn(Schedulers.boundedElastic())
                .doOnError(error ->
                        logService.log(Log.error("Error querying events: " + error.getMessage())))
                .doOnSuccess(saved ->
                        logService.log(Log.info("Events retrieved successfully (" + saved.getTotalElements() + ")")))
                .doFinally(signalType ->
                        logService.log(Log.info("** Event query completed with signal " + signalType + " **")));
    }
}
