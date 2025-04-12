package com.adt.registering.application.queries;

import com.adt.registering.domain.repositories.filters.LogFilter;
import org.springframework.data.domain.Pageable;

public record GetLogsByQuery(
        LogFilter filter,
        Pageable pageable
) {
}
