package com.adt.registering.domain.repositories.filters;

import java.time.LocalDateTime;

public record EventFilter(
        String type,
        LocalDateTime from,
        LocalDateTime to
) {
}
