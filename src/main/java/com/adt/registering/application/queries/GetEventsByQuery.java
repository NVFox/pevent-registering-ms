package com.adt.registering.application.queries;

import com.adt.registering.domain.repositories.filters.EventFilter;
import org.springframework.data.domain.Pageable;

public record GetEventsByQuery(
        EventFilter filter,
        Pageable pageable
) {
}
