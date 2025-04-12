package com.adt.registering.domain.services;

import com.adt.registering.domain.entities.Event;

public interface NotificationService {
    void notify(Event event);
}
